const orderData = {
    product_id: "",
    product_name: "", 
    unit_price: 0,
    price: 0,
    fee: 0, 
    total_price: 0,
    color_code: "",
    color_name: "", 
    size: "",
    count: 1,
    max_count: 0
}
document.addEventListener("DOMContentLoaded", () => {
    const productTitle = document.querySelector("#product-title")
    const productId = document.querySelector("#product-id")
    const productPrice = document.querySelector("#product-price")
    const productTexture = document.querySelector("#product-texture")
    const productWash = document.querySelector("#product-wash")
    const productPlace = document.querySelector("#product-place")
    const productStory = document.querySelector("#product-story")

    const btnOrder = document.querySelector(".button-order")
    const payment = document.querySelector("#payment")

    const colorOptions = document.querySelector(".color-options")
    const sizeOptions = document.querySelector(".size-options")

    const btninc = document.querySelector("#btninc")
    const btndec = document.querySelector("#btndec")
    const orderQty = document.querySelector("#order-qty")

    const imgMain = document.querySelector("#imgmain")
    const imgContents = document.querySelector("#img-contents")

    const textPrice = document.querySelector("#textPrice")
    const textFee = document.querySelector("#textFee")
    const textTotalPrice = document.querySelector("#textTotalPrice")

    const productDetail = null;

    function updateBill() {
        if ( orderData.color_code !== "" && orderData.size !== "") {
            let price = parseInt(productPrice.getAttribute("data-price"))
            orderData.product_name = productTitle.getAttribute("data-title")
            orderData.unit_price = price
            orderData.price = price*orderData.count
            orderData.fee = 200
            orderData.total_price = orderData.price + orderData.fee

            textPrice.innerText = `${price*orderData.count}`
            textFee.innerText = `${200}`
            textTotalPrice.innerText = `${price*orderData.count + 200}`
        }
    }

    // clear selection of color
    function clearColorSelection() {
        const colorStamps = document.querySelectorAll(".color-option")
        colorStamps.forEach(color => {
            color.classList.remove("selected-color-option")
        })
    }

    // clear disable of size
    function clearSizeDisable() {
        const sizeStamps = document.querySelectorAll(".size-option")
        sizeStamps.forEach(size => {
            size.classList.remove("disabled-size-option")
        })
    }

    // clear selection of color
    function clearSizeSelection() {
        const sizeStamps = document.querySelectorAll(".size-option")
        sizeStamps.forEach(size => {
            size.classList.remove("selected-size-option")
        })
    }

    btndec.addEventListener("click", () => {
        var value = parseInt(orderQty.value);
        if (value > 1) {
            orderQty.value = value - 1;
            orderData.count = orderQty.value
        }
        updateBill()
    })

    btninc.addEventListener("click", () => {
        var value = parseInt(orderQty.value);
        if (value < orderData.max_count) {
            orderQty.value = value + 1;
            orderData.count = orderQty.value
        }
        updateBill()
    })

    btnOrder.addEventListener("click", () => {
        let token = localStorage.getItem("token")
        if (token == null) {
            location.href = "profile.html"
        } else {
            fetch(`http://${HOST}:${PORT}/api/1.0/user/profile`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            .then(r => r.json())
            .then(d => {
                if ("error" in d) {
                    location.href = "profile.html"
                }
            })
            .catch(e => {console.log(e)})
        }
        payment.style.display = "block"

        window.scrollTo({
            top: payment.offsetTop,
            behavior: 'smooth'
        });
        updateBill()
    })

    const searchParams = new URLSearchParams(location.search)
    let productIdNumber = "";
    if (searchParams.has("id")) {
        productIdNumber = `${searchParams.get("id")}`
        orderData.product_id = productIdNumber
    } else {
        productIdNumber = ``
    }

    fetch(`http://${HOST}:${PORT}/api/1.0/products/details?id=${productIdNumber}`)
        .then(r => r.json())
        .then(d => {
            let data = d.data
            // console.log(data)

            // info
            productTitle.innerText = `${data.title}`
            productTitle.setAttribute("data-title", `${data.title}`)
            productId.innerText = `${data.id}`
            productPrice.innerText = `TWD.${data.price}`
            productPrice.setAttribute("data-price", `${data.price}`)
            productTexture.innerText = `材質：${data.texture}`
            productWash.innerText = `清洗：${data.wash}`
            productPlace.innerText = `產地：${data.place}`
            productStory.innerText = `${data.story}`

            // color
            let variants = data.variants
            let colors = data.colors
            colors.forEach(c => {
                colorOptions.innerHTML += `<div class="color-option" style="background-color: #${c.code};" data-code="${c.code}" data-name="${c.name}"></div>`
            })

            // size
            let sizes = data.sizes
            sizes.forEach(s => {
                sizeOptions.innerHTML += `<div class="size-option disabled-size-option" data-size="${s}"><span>${s}</div></span>`
            })

            colorStamps = document.querySelectorAll(".color-option")
            sizeStamps = document.querySelectorAll(".size-option")
            colorStamps.forEach(color => {
                color.addEventListener("click", () => {
                    clearColorSelection()
                    clearSizeDisable()
                    clearSizeSelection()
                    color.classList.add("selected-color-option")

                    orderData.color_code = color.getAttribute("data-code")
                    orderData.color_name = color.getAttribute("data-name")
                    orderData.size = "";
                    sizeStamps.forEach( size => {
                        sizeText = size.getAttribute("data-size")
                        if (variants.filter(v => v.color_code === orderData.color_code && v.size === sizeText).length === 0) {
                            size.classList.add("disabled-size-option")
                        }
                    })
                })
            });

            sizeStamps.forEach( size => {
                size.addEventListener("click", (e) => {
                    let sizeText = size.getAttribute("data-size")
                    if (!size.classList.contains("disabled-size-option")) {
                        clearSizeSelection()
                        size.classList.add("selected-size-option")
                        let v = variants.filter(v => v.color_code === orderData.color_code && v.size === sizeText)
                        orderData.max_count = v[0].stock
                        orderData.size = sizeText
                        updateBill()
                    }
                })
            })

            // images
            imgMain.src = data.main_image
            let images = data.images;
            let imgsHTML = ""
            for(let i=0;i<images.length;i++) {
                imgsHTML += `<img src="${images[i]}" alt="img">`
            }
            imgContents.innerHTML = imgsHTML;
        })
})