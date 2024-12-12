window.search = {
    setCallBtn: function() {
        document.getElementById('search-button').addEventListener('click', () => search.call());

    }, call: async function() {
        const callSearchUrl = document.getElementById('search-button').getAttribute('data-search-url');
        const params = {
            query: document.getElementById('search-keyword').value
        };
        const searchResult = await callAPIGet(callSearchUrl, params);
        this.rendering(searchResult.data);

    }, rendering: function(data) {
        const response = document.querySelector('#response-section ul');
        data.forEach(item => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                            <li>
                                <a href="${item.link}" target="_blank">${item.title}</a>
                                <p>${item.content}</p>
                            </li>
                        `;
            response.appendChild(listItem);
        })
    }
}

search.setCallBtn();