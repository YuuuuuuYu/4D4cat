window.openAI = {
    init: function() {
        openAI.setConvertBtn();
    },
    call: async function() {
        const prompt = document.getElementById('prompt-text').value;
        const url = '/nakji/api/gpt?prompt='+prompt;
        const result = await callAPIGet(url, '');

        document.querySelector('.response-area-content').innerHTML = result.data;
        document.querySelector('.response-time').innerHTML = result.time;
    },
    setConvertBtn: function() {
        document.getElementById('btn-call').addEventListener('click', openAI.call);
    }
}