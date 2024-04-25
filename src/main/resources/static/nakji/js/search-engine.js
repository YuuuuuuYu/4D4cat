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

    document.getElementById("btn_search").onclick = function() {
        doSearch().then();
    };
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
    const imgSrc = target.src.slice('/assets');
    const engine = target.alt;
    let resultSrc;

    if (!imgSrc.includes('pick')) {
        resultSrc = imgSrc.replace(engine, engine+'_pick');
    } else {
        resultSrc = imgSrc.replace('_pick', '');
    }

    target.src = resultSrc;
}
function renderingData(list) {
    let resultDiv = document.getElementById("searchResult");
    let htmlForm = `
        <h2>Search Result</h2>
        <ul>
    `;

    list.forEach(function(data) {
        htmlForm += '<li>';
        htmlForm += '<h4>'+data.title+'</h4>';
        htmlForm += '<p>'+data.content+'</p>';
        htmlForm += '<a href="'+data.link+'" target="_blank">Read more</a>';
        htmlForm += '</li>';
    });

    htmlForm += '</ul>';
    resultDiv.innerHTML = htmlForm;
}
async function doSearch() {
    const engine = document.querySelector('div#search-engine button.active');
    const query = document.getElementById("ipt_search").value;

    if(engine == null) {
        alert('검색 엔진을 선택해주세요.');
        return;
    }

    if(query === '') {
        alert('검색어를 입력해주세요.');
        return;
    }

    const url = '/nakji/api/'+engine.name+'?query='+query;
    const resultAPI = await callAPIGet(url, '');

    renderingData(resultAPI.data);
}