/* ==========================================================
   TRIPINAJA — itinerary.js
   Itinerary Detail Page — Client-side interactions
   ========================================================== */

document.addEventListener('DOMContentLoaded', function () {

    /* ---- Delete Day confirmation ---- */
    window.deleteDay = function (dayId) {
        if (!confirm('Hapus hari ini beserta semua aktivitasnya?')) return;

        fetch(`/traveler/itinerary/day/${dayId}/delete`, {
            method: 'DELETE',
            headers: { 'X-CSRF-TOKEN': getCsrfToken() }
        })
        .then(res => {
            if (res.ok) location.reload();
            else alert('Gagal menghapus hari. Coba lagi.');
        })
        .catch(() => alert('Terjadi kesalahan jaringan.'));
    };

    /* ---- Helper: get CSRF token from meta tag ---- */
    function getCsrfToken() {
        const el = document.querySelector('meta[name="_csrf"]');
        return el ? el.getAttribute('content') : '';
    }

    /* ---- Animate day cards on scroll ---- */
    const cards = document.querySelectorAll('.day-card');
    if ('IntersectionObserver' in window) {
        const observer = new IntersectionObserver(entries => {
            entries.forEach(e => {
                if (e.isIntersecting) {
                    e.target.style.opacity = '1';
                    e.target.style.transform = 'translateY(0)';
                    observer.unobserve(e.target);
                }
            });
        }, { threshold: 0.1 });

        cards.forEach((card, i) => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(24px)';
            card.style.transition = `opacity 0.4s ease ${i * 0.07}s, transform 0.4s ease ${i * 0.07}s`;
            observer.observe(card);
        });
    }

});

/* ==========================================================
   MODAL SYSTEM
   ========================================================== */

function openModal(id) {
    document.getElementById(id).classList.add('active');
}

function closeModal(id) {
    document.getElementById(id).classList.remove('active');
}

// Tutup modal kalau klik overlay
document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', function(e) {
        if (e.target === this) closeModal(this.id);
    });
});

/* ==========================================================
   DELETE DAY
   ========================================================== */

function confirmDeleteDay(id, name) {
    document.getElementById('deleteDayName').textContent = '"' + name + '"';
    openModal('modalDeleteDay');
}

/* ==========================================================
   ADD ACTIVITY (with 3 tabs)
   ========================================================== */

let currentAddDayId = null;

function openAddActivityModal(dayId) {
    currentAddDayId = dayId;
    // Reset to transport tab
    switchActivityTab('transport');
    openModal('modalAddActivity');
}

function switchActivityTab(tabName) {
    // Update tab buttons
    document.querySelectorAll('.activity-tab').forEach(tab => {
        tab.classList.toggle('active', tab.dataset.tab === tabName);
    });
    // Update tab content
    document.querySelectorAll('.activity-tab-content').forEach(content => {
        content.classList.remove('active');
    });
    const contentId = 'tabContent' + tabName.charAt(0).toUpperCase() + tabName.slice(1);
    document.getElementById(contentId).classList.add('active');
}

function saveNewActivity() {
    // TODO: Ambil data dari tab yang aktif, kirim ke server
    // const activeTab = document.querySelector('.activity-tab.active').dataset.tab;
    closeModal('modalAddActivity');
}

/* ==========================================================
   EDIT ACTIVITY (dynamic form based on type)
   ========================================================== */

let currentEditActivityId = null;
let currentEditActivityType = null;

function editActivity(activityId, activityType) {
    currentEditActivityId = activityId;
    currentEditActivityType = activityType;

    const modalBody = document.getElementById('editActivityModalBody');
    const modalTitle = document.getElementById('editActivityModalTitle');

    if (activityType === 'transport') {
        modalTitle.innerHTML = '<i class="fas fa-car"></i> Edit Transport';
        modalBody.innerHTML = getTransportEditForm();
    } else if (activityType === 'accommodation') {
        modalTitle.innerHTML = '<i class="fas fa-hotel"></i> Edit Accommodation';
        modalBody.innerHTML = getAccommodationEditForm();
    } else {
        modalTitle.innerHTML = '<i class="fas fa-umbrella-beach"></i> Edit Activity';
        modalBody.innerHTML = getActivityEditForm();
    }

    openModal('modalEditActivity');
}

function getTransportEditForm(data) {
    return `
        <p class="form-section-title"><i class="fas fa-car"></i> Transport Details</p>
        <div class="form-row">
            <div class="form-group">
                <label>Type</label>
                <select class="form-input" id="editTransType">
                    <option value="Private Car" selected>Private Car</option>
                    <option value="Bus">Bus</option>
                    <option value="Motorbike">Motorbike</option>
                    <option value="Boat">Boat</option>
                    <option value="Flight">Flight</option>
                </select>
            </div>
            <div class="form-group">
                <label>Provider</label>
                <input type="text" class="form-input" id="editTransProvider" value="Bali Transfer Co.">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label>From</label>
                <input type="text" class="form-input" id="editTransFrom" value="Ngurah Rai Airport">
            </div>
            <div class="form-group">
                <label>To</label>
                <input type="text" class="form-input" id="editTransTo" value="W Bali – Seminyak">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label>Price (USD)</label>
                <input type="number" class="form-input" id="editTransPrice" value="15">
            </div>
            <div class="form-group">
                <label>Departure Time</label>
                <input type="time" class="form-input" id="editTransTime" value="13:00">
            </div>
        </div>
    `;
}

function getAccommodationEditForm(data) {
    return `
        <p class="form-section-title"><i class="fas fa-hotel"></i> Accommodation Details</p>
        <div class="form-group">
            <label>Accommodation Name</label>
            <input type="text" class="form-input" id="editAccName" value="W Bali – Seminyak">
        </div>
        <div class="form-row">
            <div class="form-group">
                <label>Location</label>
                <input type="text" class="form-input" id="editAccLocation" value="Jl. Petitenget, Seminyak">
            </div>
            <div class="form-group">
                <label>Price / Night (USD)</label>
                <input type="number" class="form-input" id="editAccPrice" value="180">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label>Check-in Time</label>
                <input type="time" class="form-input" id="editAccTime" value="14:00">
            </div>
        </div>
    `;
}

function getActivityEditForm(data) {
    return `
        <p class="form-section-title"><i class="fas fa-umbrella-beach"></i> Activity Details</p>
        <div class="form-group">
            <label>Activity Name</label>
            <input type="text" class="form-input" id="editActName" value="Sunset Cocktails & Dinner">
        </div>
        <div class="form-row">
            <div class="form-group">
                <label>Location</label>
                <input type="text" class="form-input" id="editActLocation" value="Potato Head Beach Club">
            </div>
            <div class="form-group">
                <label>Price (USD)</label>
                <input type="number" class="form-input" id="editActPrice" value="46">
            </div>
        </div>
        <div class="form-group">
            <label>Description</label>
            <textarea class="form-input" id="editActDesc" rows="3">Enjoy iconic beachfront views and Mediterranean cuisine.</textarea>
        </div>
    `;
}

function saveEditActivity() {
    // TODO: Implement actual save logic based on currentEditActivityType
    closeModal('modalEditActivity');
}

/* ==========================================================
   DELETE ACTIVITY
   ========================================================== */

let currentDeleteActivityId = null;

function confirmDeleteActivity(activityId, activityName) {
    currentDeleteActivityId = activityId;
    document.getElementById('deleteActivityName').textContent = '"' + activityName + '"';
    openModal('modalDeleteActivity');
}

function executeDeleteActivity() {
    // TODO: Implement actual delete logic using currentDeleteActivityId
    closeModal('modalDeleteActivity');
}
