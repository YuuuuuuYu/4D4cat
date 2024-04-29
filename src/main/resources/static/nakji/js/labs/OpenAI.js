window.openAI = {
    init: function() {
        openAI.setConvertBtn();
    },
    call: async function() {
        const callButton = this;
        const loading = document.getElementById('btn-loading');
        callButton.disabled = true; // 버튼 비활성화
        callButton.classList.add('button-disabled'); // 로딩 중 스타일 적용
        callButton.innerText = 'Loading...'; // 버튼 텍스트 변경
        loading.classList.remove('hidden'); // 로딩 애니메이션 표시

        const prompt = document.getElementById('prompt-text').value;
        const url = '/nakji/api/gpt?prompt='+prompt;
        const result = await callAPIGet(url, '');

        document.querySelector('.response-area-content').innerHTML = result.data;
        document.querySelector('.response-time').innerHTML = result.time;

        callButton.disabled = false;
        callButton.classList.remove('button-disabled'); // 원래 스타일로 복원
        callButton.innerText = 'Call'; // 버튼 텍스트 복원
        loading.classList.add('hidden'); // 로딩 애니메이션 숨김
    },
    setConvertBtn: function() {
        document.getElementById('btn-call').addEventListener('click', openAI.call);
    }
}