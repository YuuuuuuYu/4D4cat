window.openai = {
    setConvertBtn: function() {
        document.getElementById('openai-button').addEventListener('click', openai.call);

    }, call: async function() {
        const button = document.getElementById('openai-button');
        const spinner = document.getElementById('openai-spinner');
        const promptInput = document.getElementById('openai-prompt');
        const responsePre = document.querySelector('.response-section pre');
        const responseTime = document.querySelector('.response-time');

        button.disabled = true;
        spinner.style.display = 'inline-block';

        try {
            const url = '/labs/openai/call';
            const params = {
                prompt: promptInput.value
            };
            const result = await callAPIGet(url, params);
            responsePre.innerHTML = result.data;
            responseTime.innerHTML = result.time;

        } catch (error) {
            responsePre.innerHTML = '에러가 발생했습니다. 콘솔을 확인해주세요.';
            console.error('API 호출 에러:', error);

        } finally {
            button.disabled = false;
            spinner.style.display = 'none';
        }
    }
}

window.woorimail = {
    init: async function () {
        document.getElementById('subscribe-button').addEventListener('click', function () {
            document.getElementsByClassName('popup-layer')[0].style.display = 'block';
        });

        document.getElementById('close-button').addEventListener('click', popup.closePopup);

        popup.setClickBtn();
    },
    call: async function() {
        const json = {};
        json.nickname = document.getElementById('subscribe-nickname').value;
        json.email = document.getElementById('subscribe-email').value

        const resultApi = await callAPIPost('/nakji/api/woorimail/send', json);
        if (!isEmptyOrUndefined(resultApi.data))
            alert(resultApi);

        popup.closePopup();
    },
    setClickBtn: function() {
        document.querySelector('#subscribe-form button').addEventListener('click', popup.call);
    },
    closePopup: function() {
        document.getElementsByClassName('popup-layer')[0].style.display = 'none';
    }
}

openai.setConvertBtn();