/* =========================================================
   TRAVELER PROFILE PAGE JS
========================================================= */

// Edit nama — show/hide form yang sudah ada di HTML
function enableEditMode(e) {
    e.preventDefault();
    document.getElementById('view-mode').style.display = 'none';
    document.getElementById('edit-mode').style.display = 'grid';
}

function disableEditMode() {
    document.getElementById('view-mode').style.display = 'grid';
    document.getElementById('edit-mode').style.display = 'none';
}

// Change password — pakai modal yang sudah ada di HTML
function openChangePasswordModal() {
    document.getElementById('passwordModal').style.display = 'flex';
}

function closeChangePasswordModal() {
    document.getElementById('passwordModal').style.display = 'none';
}

function confirmLogout(e) {
    return confirm('Are you sure you want to log out?');
}

function showToast(msg, type = 'success') {
    const existing = document.querySelector('.toast-notification');
    if (existing) existing.remove();

    const toast = document.createElement('div');
    toast.className = 'toast-notification';
    toast.textContent = msg;
    toast.style.cssText = `
        position: fixed;
        bottom: 24px;
        right: 24px;
        background: ${type === 'error' ? '#dc2626' : '#111827'};
        color: #ffffff;
        padding: 16px 28px;
        border-radius: 14px;
        font-size: 14px;
        font-weight: 500;
        z-index: 10000;
        animation: slideIn 0.3s ease;
        box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        font-family: 'Poppins', sans-serif;
    `;
    document.body.appendChild(toast);
    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Auto convert flash message Thymeleaf ke toast
document.addEventListener('DOMContentLoaded', function() {
    const flashMessages = document.querySelectorAll('.flash-message');
    flashMessages.forEach(msg => {
        const type = msg.classList.contains('error') ? 'error' : 'success';
        showToast(msg.textContent.trim(), type);
        msg.remove(); 
    });
});

// Keyframe animations
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from { transform: translateX(100px); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }
    @keyframes slideOut {
        from { transform: translateX(0); opacity: 1; }
        to { transform: translateX(100px); opacity: 0; }
    }
`;
document.head.appendChild(style);