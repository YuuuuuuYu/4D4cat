var nakji = {

    init: function () {
        var _this = this;
        document.getElementById('btn_search').addEventListener('click', function () {
            //_this.searchNaver();
        });
        _this.setSearchEngine();
    },
    setSearchEngine: function() {
        let engineFrame = document.getElementById('search-engine');
        const searchBtns = [
            { id: 'naver', img_on: '/assets/img/search/icon_naver.png', img_click: '/assets/img/search/icon_naver_click.png' },
            { id: 'google', img_on: '/assets/img/search/icon_google.png', img_click: '/assets/img/search/icon_google_click.png' }
        ];

        for (const btn of searchBtns) {
            const button = document.createElement('button');
            button.type = 'button';
            button.className = 'btn';
            button.id = 'btn_search_'+btn.id;

            let img = document.createElement('img');
            img.src = btn.img_on;
            img.alt = btn.id;

            this.toggleSearchEngine(button, img, btn.img_on, btn.img_click);
            button.appendChild(img);
            engineFrame.appendChild(button);
        }
    },
    toggleSearchEngine: function(btn, img, prevImg, activeImg) {
        btn.addEventListener('click', function () {
            let activeImgs = document.querySelectorAll('button.active');
            activeImgs.forEach(target => target.classList.remove('active'));
            btn.classList.add('active');
        });
        /* 검토 필요
        btn.addEventListener('classListchange', function (e) {console.log(e);
            const targetClass = e.detail;
            if (targetClass !== 'active') return;

            img.src = prevImg;
            img.src = activeImg;

        });
         */
    },
    searchNaver: async function () {
        let query = document.getElementById("ipt_search").value;
        let resultDiv = document.getElementById("searchResult");
        const response = await fetch("/nakji/api/naver.text?query="+query)

            .then(response => {
                if (response.ok) {
                    return response.text();

                } else {
                    throw new Error("네트워크 응답이 정상적이지 않습니다");
                }
        });

        resultDiv.innerHTML = response;
    },
    searchGoogle: async function () {
        let query = document.getElementById("ipt_search").value;
        let resultDiv = document.getElementById("searchResult");
        const response = await fetch("/nakji/api/google?query="+query)

            .then(response => {
                if (response.ok) {
                    return response.text();

                } else {
                    throw new Error("네트워크 응답이 정상적이지 않습니다");
                }
            });

        resultDiv.innerHTML = response;
    }
};

nakji.init();