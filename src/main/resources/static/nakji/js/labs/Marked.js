import {marked} from "https://cdn.jsdelivr.net/npm/marked/lib/marked.esm.js";

window.Marked = {
    init: async function() {
        const sampleMarkdownTxt = await fetch('/nakji/sample/markdown-sample.txt');
        const preText = await sampleMarkdownTxt.text();
        document.getElementById('edit-text').value = preText;
        document.getElementById('preview-area').innerHTML = marked.parse(preText);

        Marked.setConvertBtn();
    },
    convert: function() {
        const edit_text = document.getElementById('edit-text').value;
        document.getElementById('preview-area').innerHTML = marked.parse(edit_text);
    },
    setConvertBtn: function() {
        document.getElementById('btn-convert').addEventListener('click', Marked.convert);
    }
};