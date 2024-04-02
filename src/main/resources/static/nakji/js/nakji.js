window.onload = function () {
    setSearchEngine();
    toggleSearchEngine();
    renderingPage('/nakji/page/intro', '');
};

async function renderingPage(url, data) {
    const template = await callAPIGet(url, '');
    const page = Mustache.render(template, data);

    document.getElementById('page').innerHTML = page;
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