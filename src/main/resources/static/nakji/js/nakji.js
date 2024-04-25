window.onload = function () {
    setSearchEngine();
    toggleSearchEngine();
    renderingPage('/nakji/page/intro');
};

async function renderingPage(url, func) {
    const result = await callAPIGet(url, '');
    const template = result.data;
    const page = Mustache.render(template, '');

    document.getElementById('page').innerHTML = page;
    if (func !== undefined) func.init();
}

async function callAPIGet(url, params) {
    let result = [];
    const startTime = Date.now();

    await axios.get(url, {params: params})
        .then(response => {
            const endTime = Date.now();
            result.time = ((endTime - startTime) / 1000).toFixed(2) + 's';

            if (response.status === 200) {
                result.data = response.data;

            } else {
                console.log(response.status);

            }
        })
        .catch(error => {
            console.log(error);

        });

    return result;
}