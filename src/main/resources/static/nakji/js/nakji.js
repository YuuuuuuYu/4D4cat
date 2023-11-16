window.onload = function() {
    setSearchEngine();
    toggleSearchEngine();
};

function renderingPage(form) {
    let resultDiv = document.getElementById("page");
    const htmlForm = form;

    resultDiv.innerHTML = htmlForm;
}

function callAPI(url) {
    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.json();

            } else {
                throw new Error("네트워크 응답이 정상적이지 않습니다");
            }
        });

    return false;
}