/**
 * Auth Module - Mengelola feedback alerts dan form validation
 */

document.addEventListener('DOMContentLoaded', function () {
    // Inisialisasi close button untuk alerts
    initializeAlertClose();

    // Hapus success message setelah 5 detik
    autoHideSuccessAlert();

    // Tambah visual feedback saat form submit
    initializeFormFeedback();
});

/**
 * Initialize alert close buttons
 */
function initializeAlertClose() {
    const closeButtons = document.querySelectorAll('.auth-alert-close');
    closeButtons.forEach(btn => {
        btn.addEventListener('click', function () {
            const alert = this.closest('.auth-alert');
            alert.style.animation = 'slideOutUp 0.3s ease forwards';
            setTimeout(() => {
                alert.style.display = 'none';
            }, 300);
        });
    });
}

/**
 * Auto-hide success alerts after 5 seconds
 */
function autoHideSuccessAlert() {
    const successAlerts = document.querySelectorAll('.auth-alert-success');
    successAlerts.forEach(alert => {
        setTimeout(() => {
            alert.style.animation = 'slideOutUp 0.3s ease forwards';
            setTimeout(() => {
                alert.style.display = 'none';
            }, 300);
        }, 5000);
    });
}

/**
 * Initialize form feedback on input
 */
function initializeFormFeedback() {
    const inputs = document.querySelectorAll('.input-group input');
    inputs.forEach(input => {
        input.addEventListener('blur', function () {
            validateInput(this);
        });

        input.addEventListener('focus', function () {
            this.classList.remove('input-error');
        });
    });
}

/**
 * Validate individual input
 */
function validateInput(input) {
    if (input.hasAttribute('required') && input.value.trim() === '') {
        input.classList.add('input-error');
        return false;
    }

    if (input.type === 'email' && input.value.trim() !== '' && !isValidEmail(input.value)) {
        input.classList.add('input-error');
        return false;
    }

    input.classList.remove('input-error');
    return true;
}

/**
 * Email validation helper
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * CSS animation for slide out up
 */
const style = document.createElement('style');
style.textContent = `
    @keyframes slideOutUp {
        from {
            opacity: 1;
            transform: translateY(0);
        }
        to {
            opacity: 0;
            transform: translateY(-10px);
        }
    }
    
    .input-error {
        border-color: #dc2626 !important;
        background: #fef2f2 !important;
    }
`;
document.head.appendChild(style);

