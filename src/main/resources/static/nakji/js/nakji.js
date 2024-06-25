window.onload = function () {
    let empty;
    const initBindingObj = parseRssFeedToIntro();
    setSearchEngine();
    toggleSearchEngine();

    initBindingObj.then(obj => {
        renderingPage('/nakji/page/intro', empty, obj);
    });
};

async function renderingPage(url, func, bindingObj) {
    const result = !isEmptyOrUndefined(bindingObj) ? await callAPIPost(url, bindingObj) : await callAPIGet(url, bindingObj);
    const template = result.data;
    const page = Mustache.render(template, '');

    document.getElementById('page').innerHTML = page;
    if (!isEmptyOrUndefined(func)) func.init();
}

async function callAPI(method, url, params) {
    let result = [];
    const startTime = Date.now();
    const options = {
        method: method,
        url: url
    };

    if (method === 'GET') options.params = params;
    else                    options.data = params;

    await axios(options)
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

async function callAPIGet(url, params) {
    return await callAPI('GET', url, params);
}

async function callAPIPost(url, params) {
    return await callAPI('POST', url, params);
}

async function parseRssFeedToIntro() {
    const feedUrl = 'https://yuuuuuuyu.github.io/feed.xml';
    const parser = new DOMParser();
    const resultXml = await callAPIGet(feedUrl, '');
    const xmlDoc = parser.parseFromString(resultXml.data, "text/xml");
    const result = [];

    const entries = Array.from(xmlDoc.getElementsByTagName("entry"));
    entries.slice(0, 4).forEach(entry => {  // 상위 포스트 4개만
        let json = {};
        const title = entry.getElementsByTagName("title")[0].textContent;
        const link = entry.getElementsByTagName("link")[0].getAttribute("href");

        json.title = title;
        json.link = link;
        result.push(json);
    });

    return result;
}

function isEmptyOrUndefined(value) {
    return value === undefined || value === '';
}