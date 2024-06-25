window.popup = {
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