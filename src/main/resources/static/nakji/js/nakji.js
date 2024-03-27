window.onload = function() {
    setSearchEngine();
    toggleSearchEngine();
};

function renderingPage(form) {
    document.getElementById("page").innerHTML = form;
}

async function callAPIGet(url, params) {
    let result = [];
    await axios.get(url, {params: params})
        .then(response => {
            if (response.status === 200) {
                result = response.data;

            } else {
                console.log(response.status);

            }
        })
        .catch(error => {
            console.log(error);

        });

    return result;
}