window.onload = function () {
    setSearchEngine();
    toggleSearchEngine();
    renderingPage('/nakji/page/intro', '');
};

async function renderingPage(url, script) {
    const template = await callAPIGet(url, '');
    const page = Mustache.render(template, '');

    document.getElementById('page').innerHTML = page;

    if (script !== '') {
        try {
            const jsonForm = "\'"+script.replaceAll("\'", '\"') + "\'";
            const scriptJson = JSON.parse(jsonForm);
            const js = document.createElement('script');

            js.type = scriptJson.type;
            js.src = scriptJson.src;
            document.body.appendChild(js);

        } catch (error) {
            console.error(error.message);
            return {};
        }
    }
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