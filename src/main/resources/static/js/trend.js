const groupedData = {};
let currentDate;

window.trend = {
    init: async function() {
        const yesterday = new Date(Date.now()-86400000);
        const oneMonthAgo = new Date(Date.now()-86400000*30).toISOString().split('T')[0];
        currentDate = yesterday.toISOString().split('T')[0];

        const callTrendDataUrl = `/trend/day?start=${oneMonthAgo.split('-').join('')}&end=${currentDate.split('-').join('')}`;
        document.getElementById('currentDate').innerText = currentDate;

        const collectionData = await callAPIGet(callTrendDataUrl, '');
        collectionData.data.forEach(item => {
            const {collectedDate: date, ...rest} = item;
            if (!groupedData[date]) {
                groupedData[date] = [];
            }
            groupedData[date].push(rest);
        });
        await trend.updateNewsList(currentDate);

        document.getElementById('prevDate').addEventListener('click', () => trend.changeDate('prev'));
        document.getElementById('nextDate').addEventListener('click', () => trend.changeDate('next'));
    }, updateNewsList: async function(date) {
        const newsList = document.getElementById('newsList');
        newsList.innerHTML = ''; // Clear the list

        if (groupedData[date]) {
            const sortedNews = groupedData[date].sort((a, b) => a.rank - b.rank).slice(0, 10); // Sort and limit to 10
            sortedNews.forEach(news => {
                const listItem = document.createElement('li');
                listItem.classList.add('news-card');
                listItem.innerHTML = `
                            <img class="news-thumbnail" src="${news.imgUrl}" alt="Thumbnail">
                            <div class="news-content">
                                <a href="${news.articleUrl}" target="_blank" class="news-title">${news.keywordName}</a>
                            </div>
                        `;
                newsList.appendChild(listItem);
            });
        }
    }, changeDate: async function(direction) {
        const btnPrevDate = document.getElementById('prevDate').classList;
        const btnNextDate = document.getElementById('nextDate').classList;
        const dates = Object.keys(groupedData);
        let currentIndex = dates.indexOf(currentDate);

        if (direction === 'prev' && currentIndex > 0) {
            currentIndex--;
        } else if (direction === 'next' && currentIndex < dates.length - 1) {
            currentIndex++;
        }

        currentDate = dates[currentIndex];
        if (currentIndex === 0) btnPrevDate.add('arrow-disabled');
        else btnPrevDate.remove('arrow-disabled');

        if (currentIndex === dates.length-1) btnNextDate.add('arrow-disabled');
        else btnNextDate.remove('arrow-disabled');

        document.getElementById('currentDate').innerText = currentDate;
        await trend.updateNewsList(currentDate);
    }
}

trend.init();