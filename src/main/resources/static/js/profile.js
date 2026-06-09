/* =========================================================
   TRAVELER PROFILE PAGE JS
========================================================= */

function enableEditMode(e) {
    e.preventDefault();
    const inputs = document.querySelectorAll('.form-group input');
    const link = e.target;
    inputs.forEach(i => { 
        i.removeAttribute('readonly'); 
        i.style.background = '#ffffff'; 
        i.style.borderColor = '#A57249'; 
    });
    link.textContent = 'Save Changes'; 
    link.style.color = '#16a34a';
    link.onclick = function(ev) { 
        ev.preventDefault(); 
        inputs.forEach(i => { 
            i.setAttribute('readonly', true); 
            i.style.background = '#F6F7FB'; 
            i.style.borderColor = '#ECECEC'; 
        }); 
        link.textContent = 'Edit Info'; 
        link.style.color = '#A57249'; 
        link.onclick = enableEditMode; 
        showToast('Profile updated!'); 
    };
}

function openChangePasswordModal() {
    const modal = document.createElement('div');
    modal.className = 'modal-overlay';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Change Password</h3>
                <button class="modal-close" onclick="this.closest('.modal-overlay').remove()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Current Password</label>
                    <input type="password" placeholder="Enter current password">
                </div>
                <div class="form-group">
                    <label>New Password</label>
                    <input type="password" placeholder="Enter new password">
                </div>
                <div class="form-group">
                    <label>Confirm New Password</label>
                    <input type="password" placeholder="Confirm new password">
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn-cancel" onclick="this.closest('.modal-overlay').remove()">Cancel</button>
                <button class="btn-save" onclick="showToast('Password changed!'); this.closest('.modal-overlay').remove();">Save Password</button>
            </div>
        </div>
    `;
    document.body.appendChild(modal);
    modal.addEventListener('click', function(e) {
        if (e.target === modal) modal.remove();
    });
}

function toggle2FA(cb) { 
    showToast('Two-Factor ' + (cb.checked ? 'enabled' : 'disabled')); 
}

function openLiveChat(e) { 
    e.preventDefault(); 
    showToast('Opening live chat...'); 
}

function confirmLogout(e) { 
    return confirm('Are you sure you want to log out?'); 
}

function showToast(msg) {
    const existing = document.querySelector('.toast-notification');
    if (existing) existing.remove();

    const toast = document.createElement('div');
    toast.className = 'toast-notification';
    toast.textContent = msg;
    toast.style.cssText = `
        position: fixed;
        bottom: 24px;
        right: 24px;
        background: #111827;
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

// Add keyframe animations
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

    .modal-overlay {
        position: fixed;
        top: 0; left: 0; right: 0; bottom: 0;
        background: rgba(0,0,0,0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 10000;
        animation: fadeIn 0.2s ease;
    }

    @keyframes fadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }

    .modal-content {
        background: #ffffff;
        border-radius: 28px;
        width: 90%;
        max-width: 440px;
        padding: 32px;
        animation: slideUp 0.3s ease;
    }

    @keyframes slideUp {
        from { transform: translateY(20px); opacity: 0; }
        to { transform: translateY(0); opacity: 1; }
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 28px;
    }

    .modal-header h3 {
        font-size: 20px;
        font-weight: 600;
        color: #111827;
    }

    .modal-close {
        background: none;
        border: none;
        font-size: 28px;
        cursor: pointer;
        color: #7B8190;
        line-height: 1;
    }

    .modal-body .form-group {
        margin-bottom: 18px;
    }

    .modal-body label {
        display: block;
        font-size: 12px;
        color: #7B8190;
        margin-bottom: 8px;
        text-transform: uppercase;
        font-weight: 500;
    }

    .modal-body input {
        width: 100%;
        padding: 14px 16px;
        border: 1px solid #ECECEC;
        border-radius: 14px;
        font-size: 15px;
        font-family: 'Poppins', sans-serif;
        outline: none;
        transition: border-color 0.3s;
    }

    .modal-body input:focus {
        border-color: #A57249;
    }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 14px;
        margin-top: 28px;
    }

    .btn-cancel {
        padding: 12px 24px;
        border: 1.5px solid #ECECEC;
        border-radius: 14px;
        background: #ffffff;
        color: #111827;
        font-size: 14px;
        font-weight: 500;
        font-family: 'Poppins', sans-serif;
        cursor: pointer;
        transition: all 0.3s;
    }

    .btn-cancel:hover {
        border-color: #A57249;
        color: #A57249;
    }

    .btn-save {
        padding: 12px 24px;
        border: none;
        border-radius: 14px;
        background: #A57249;
        color: #ffffff;
        font-size: 14px;
        font-weight: 600;
        font-family: 'Poppins', sans-serif;
        cursor: pointer;
        transition: all 0.3s;
    }

    .btn-save:hover {
        background: #7c5433;
    }
`;
document.head.appendChild(style);