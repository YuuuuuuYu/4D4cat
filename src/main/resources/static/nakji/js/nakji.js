window.onload = function() {
    setSearchEngine();
    toggleSearchEngine();
};
function setSearchEngine() {
    let engineFrame = document.getElementById('search-engine');
    const searchBtns = [
        { id: 'naver', img_on: '/assets/img/search/icon_naver.png', func: 'searchNaver' },
        { id: 'google', img_on: '/assets/img/search/icon_google.png', func: 'searchGoogle' }
    ];

    for (const btn of searchBtns) {
        const button = document.createElement('button');
        button.type = 'button';
        button.className = 'btn';
        button.id = 'btn_search_'+btn.id;
        button.name = btn.id;

        let img = document.createElement('img');
        img.src = btn.img_on;
        img.alt = btn.id;

        button.appendChild(img);
        engineFrame.appendChild(button);
    }
}
function toggleSearchEngine() {
    const btns = document.querySelectorAll('div#search-engine button');
    btns.forEach(btn => {
        btn.addEventListener('click', function () {
            if (btn.classList.contains('active')) return;

            const img = btn.querySelector('img');
            let activeBtn = document.querySelectorAll('button.active');

            btn.classList.add('active');
            toggleSearchImg(img);

            activeBtn.forEach(target => {
                target.classList.remove('active');
                toggleSearchImg(target.querySelector('img'));
            });
        });
    });
}
function toggleSearchImg(target) {
    const imgSrc = target.src;
    if (!imgSrc.includes('pick')) {
        const resultSrc = imgSrc.split('.');
        target.src = resultSrc[0] + '_pick.' + resultSrc[1];
    } else {
        target.src = imgSrc.replace('_pick', '');
    }
}
async function doSearch() {
    const engine = document.querySelector('div#search-engine button.active');
    let query = document.getElementById("ipt_search").value;
    let resultDiv = document.getElementById("searchResult");

    if(engine == null) {
        alert('검색 엔진을 선택해주세요.');
        return;
    }

    if(query === '') {
        alert('검색어를 입력해주세요.');
        return;
    }

    let url = '/nakji/api/'+engine.name+'?query='+query;
    const response = await fetch(url)
        .then(response => {
            if (response.ok) {
                return response.text();

            } else {
                throw new Error("네트워크 응답이 정상적이지 않습니다");
            }
        });

    resultDiv.innerHTML = response;
}