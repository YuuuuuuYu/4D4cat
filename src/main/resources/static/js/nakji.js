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