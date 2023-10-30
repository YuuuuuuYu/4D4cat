var nakji = {
    init : function () {
        var _this = this;
        document.getElementById('btn_search').addEventListener('click', function () {
            _this.searchNaver();
        });
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
    }
};

nakji.init();