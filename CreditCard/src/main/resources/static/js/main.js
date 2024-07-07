const getCurrentOffer = () => {
    return fetch("/api/current-offer")
        .then(response => response.json());
}

const getProducts = async () => {
    return fetch("/api/products")
        .then(response => response.json());
}


const addProduct = (productId, price) => {
    return fetch(`/api/add-product/${productId}?price=${price}`, {
        method: "POST"
    });
}

const acceptOffer = (acceptOfferRequest) => {
    return fetch(`/api/accept-offer`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(acceptOfferRequest)
    })
        .then(response => response.json());
}

const refreshOffer = () => {
    const totalEl = document.querySelector('#offerTotal');
    const itemsCountEl = document.querySelector('#offerItemsCount');

    getCurrentOffer()
        .then(offer => {
            totalEl.textContent = `${offer.total} PLN`;
            itemsCountEl.textContent = `${offer.itemsCount}`;

            console.log(offer);
            console.log(offer.itemsCount);
        })
}

const checkoutForm = document.querySelector("#checkout");
checkoutForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const acceptOfferRequest = {
        fname: checkoutForm.querySelector('input[name="firstname"]').value,
        lname: checkoutForm.querySelector('input[name="lastname"]').value,
        email: checkoutForm.querySelector('input[name="email"]').value
    };

    acceptOffer(acceptOfferRequest)
        .then(reservationDetails => window.location.href = reservationDetails.paymentUrl);
});

const createProductHtmlEl = (productData) => {
    const template = `
        <div>
            <h4>${productData.name}</h4>
            <img src="${productData.image}" alt="${productData.name}"/>
            <span>${productData.price}</span>
            <button data-id="${productData.id}">Add to cart</button>
        </div>
    `;

    const htmlEl = document.createElement("li");
    htmlEl.innerHTML = template.trim();
    return htmlEl;
}

const initializeCartHandler = (productHtmlEl) => {
    const addToCartBtn = productHtmlEl.querySelector("button[data-id]")
    addToCartBtn.addEventListener("click", () => {
        const productId = event.target.getAttribute("data-id");
        const productPrice = parseFloat(productHtmlEl.querySelector("span").textContent);

        addProduct(productId, productPrice)
            .then(() => refreshOffer());

        console.log(`I'm going to add product: ${productId}`);
    });

    return productHtmlEl;
}

document.addEventListener("DOMContentLoaded", () => {
    const productsListEl = document.querySelector('#productList');

    getProducts()
        .then(productsAsJson => productsAsJson.map(createProductHtmlEl))
        .then(productAsHtml => productAsHtml.map(initializeCartHandler))
        .then(productsAsHtml => {
            productsAsHtml.forEach(el => productsListEl.appendChild(el))
        });

    getCurrentOffer()
        .then(offer => refreshOffer(offer));
});
