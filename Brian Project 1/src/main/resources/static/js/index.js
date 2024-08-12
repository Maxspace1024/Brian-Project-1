document.addEventListener("DOMContentLoaded", () => {
    const imgCampaign = document.querySelector("#imgcampaign");
    const imgStory = document.querySelector("#imgstory")
    const indexContainer = document.querySelector(".index-container")

    const searchParams = new URLSearchParams(location.search)
    let productAPI = "";
    if (searchParams.has("category")) {
        console.log(searchParams.get("category"))
        productAPI = `${searchParams.get("category")}`
    } else {
        productAPI = `all`
    }

    // set campaign
    fetch(`http://${HOST}:${PORT}/api/1.0/marketing/campaigns`)
        .then(r => r.json())
        .then(d => {
            let data = d.data
            if (data.length>0) {
                imgCampaign.src= data[0].picture
                imgStory.innerText = data[0].story
            }
        })


    fetch(`http://${HOST}:${PORT}/api/1.0/products/${productAPI}`)
        .then(r => r.json())
        .then(d => {
            let data = d.data

            for(let i=0;i<data.length;i++) {
                let card = `
                <div class="index-card">
                    <a href="product.html?id=${data[i].id}"><img class="cardimg" src="${data[i].main_image}"></a>
                    <div class="color-stamp-container">`
                let variants = data[i].variants
                for(let j=0;j<variants.length;j++) {
                    card += `<div class="color-stamp" style="background-color: #${variants[j].color_code};"></div>`
                }
                card += `</div>
                    <span class="card-title">${data[i].title}</span>
                    <span class="card-price">TWD.${data[i].price}</span>
                </div>
                `

                indexContainer.innerHTML += card
            }

        })
})