import {marked} from "https://cdn.jsdelivr.net/npm/marked/lib/marked.esm.js";

export const Marked = {
    init: function() {
        const preText = `
# 마크다운 예시

이것은 마크다운 형식의 **본문 텍스트**입니다. _이탤릭체_와 **굵은 글씨**를 사용할 수 있습니다.

## 목록

- 항목 1
- 항목 2
  - 하위 항목 2.1
  - 하위 항목 2.2

## 링크

[Google](https://www.google.com)

## 이미지

![이미지 대체 텍스트](이미지_주소.jpg)

## 코드

\`인라인 코드\`는 이렇게 사용합니다.
`;
        document.getElementById('edit-text').innerText = preText;
        document.getElementById('preview-area').innerHTML = marked.parse(preText);

        Marked.setConvertBtn();
    },
    convert: function() {
        const edit_text = document.getElementById('edit-text').value;
        document.getElementById('preview-area').innerHTML = marked.parse(edit_text);
    },
    setConvertBtn: function() {
        document.addEventListener('DOMContentLoaded', () => {
            document.getElementById('btn-convert').addEventListener('click', Marked.convert);
        });
    }
};