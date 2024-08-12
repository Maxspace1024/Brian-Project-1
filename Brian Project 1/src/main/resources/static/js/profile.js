document.addEventListener("DOMContentLoaded", () => {
    const profileBlock = document.querySelector("#profileBlock")
    const loginBlock = document.querySelector("#loginBlock")
    const signupBlock = document.querySelector("#signupBlock")

    const loginStatus = document.querySelector("#loginStatus")
    const signupStatus = document.querySelector("#signupStatus")

    const btnLogin = document.querySelector("#btnLogin")
    const btnSignup = document.querySelector("#btnSignup")
    const linkSignup = document.querySelector("#linkSignup")

    const account = document.querySelector("#account")
    const password = document.querySelector("#password")

    const susername = document.querySelector("#susername")
    const saccount = document.querySelector("#saccount")
    const spassword = document.querySelector("#spassword")

    const pusername = document.querySelector("#pusername")
    const paccount = document.querySelector("#paccount")
    const ppicture = document.querySelector("#ppicture")

    const token = localStorage.getItem("token")
    if (token == null) {
        loginBlock.style.display="block"
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
                loginBlock.style.display = "block"
            } else {
                profileBlock.style.display = "block"
                let data = d.data
                pusername.value = data.name
                paccount.value = data.email
                ppicture.value = data.picture
            }
        })
        .catch(e => {console.log(e)})
    }

    linkSignup.addEventListener("click", () => {
        loginBlock.style.display = "none"
        signupBlock.style.display = "block"
    })

    btnLogin.addEventListener("click", () => {
        let loginData = {
            provider: "native", 
            email: account.value,
            password: password.value
        }
        fetch(`http://${HOST}:${PORT}/api/1.0/user/signin`, {
            method: "POST", 
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        })
        .then(r => r.json())
        .then(d => {
            if ("error" in d) {
                loginStatus.innerText = d.error
            } else {
                localStorage.setItem("token", d.data.access_token)
                location.reload()
            }
        })
        .catch(e => {
            loginStatus.innerText = "login fail"   
        })
    })

    btnSignup.addEventListener("click", () => {
        let signupData = {
            name: susername.value, 
            email: saccount.value,
            password: spassword.value
        }
        fetch(`http://${HOST}:${PORT}/api/1.0/user/signup`, {
            method: "POST", 
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(signupData)
        })
        .then(r => r.json())
        .then(d => {
            if ("error" in d) {
                signupStatus.innerText = d.error   
            } else {
                location.reload()
            }   
        })
        .catch(e => {
            signupStatus.innerText = "signup fail"   
        })
    })
})