document.addEventListener('DOMContentLoaded', () => {
    const htmlElement = document.documentElement; // <html>

    const savedTheme = localStorage.getItem('theme') || 'light';
    htmlElement.setAttribute('data-theme', savedTheme);

});