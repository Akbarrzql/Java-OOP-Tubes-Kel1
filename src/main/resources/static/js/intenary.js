// ===== EVENT DELEGATION (SATU AJA!) =====
document.addEventListener('click', function(e) {
    // Hapus Hari
    const btnDeleteDay = e.target.closest('.btn-delete-day');
    if (btnDeleteDay) {
        e.preventDefault();
        confirmDeleteDay(
            btnDeleteDay.dataset.itin,
            btnDeleteDay.dataset.day,
            btnDeleteDay.dataset.name
        );
        return;
    }

    // Tambah Aktivitas
    const btnAddActivity = e.target.closest('.btn-add-activity');
    if (btnAddActivity) {
        e.preventDefault();
        openAddActivityModal(btnAddActivity.dataset.itin, btnAddActivity.dataset.day);
        return;
    }

    // Edit Aktivitas
    const btnEdit = e.target.closest('.btn-edit');
    if (btnEdit) {
        e.preventDefault();
        openEditModal(
            btnEdit.dataset.id,
            btnEdit.dataset.type,
            btnEdit.dataset.itin,
            btnEdit.dataset.day,
            btnEdit.dataset   // pass semua dataset
        );
        return;
    }

    // Hapus Aktivitas
    const btnDeleteActivity = e.target.closest('.btn-delete-activity');
    if (btnDeleteActivity) {
        e.preventDefault();
        confirmDeleteActivity(
            btnDeleteActivity.dataset.type,
            btnDeleteActivity.dataset.id,
            btnDeleteActivity.dataset.itin,
            btnDeleteActivity.dataset.day,
            btnDeleteActivity.dataset.name
        );
        return;
    }
});

// ===== SWITCH TAB =====
function switchActivityTab(tab) {
    document.querySelectorAll('.activity-tab').forEach(b => {
        b.classList.toggle('active', b.dataset.tab === tab);
    });
    document.querySelectorAll('.activity-tab-content').forEach(content => {
        content.classList.remove('active');
    });
    const tabNames = {
        'transport': 'tabContentTransport',
        'accommodation': 'tabContentAccommodation', 
        'destinasi': 'tabContentDestinasi'
    };
    const target = document.getElementById(tabNames[tab]);
    if (target) target.classList.add('active');
}

// ===== OPEN ADD ACTIVITY MODAL =====
function openAddActivityModal(itin, dayId) {
    document.getElementById('formTransport').action     = `/itinerary/${itin}/day/${dayId}/transport/add`;
    document.getElementById('formAccommodation').action = `/itinerary/${itin}/day/${dayId}/accommodation/add`;
    document.getElementById('formDestinasi').action     = `/itinerary/${itin}/day/${dayId}/destinasi/add`;
    switchActivityTab('transport');
    openModal('modalAddActivity');
}

// ===== EDIT MODAL =====
function openEditModal(id, type, itinId, dayId, dataset) {
    const title = document.getElementById('editActivityModalTitle');
    const body  = document.getElementById('editActivityModalBody');
    const form  = document.getElementById('formEdit');

    if (type === 'transport') {
        title.innerHTML = '<i class="fas fa-car"></i> Edit Transportasi';
        form.action = `/itinerary/${itinId}/day/${dayId}/transport/${id}/edit`;
        body.innerHTML = `
            <p class="form-section-title"><i class="fas fa-car"></i> Detail Transportasi</p>
            <div class="form-group">
                <label>Pilih Transport</label>
                <select class="form-input" name="transportId" id="editTransportId" required>
                    <option value="">-- Pilih Transport --</option>
                    ${buildTransportOptions(dataset.transportId)}
                </select>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Waktu Berangkat</label>
                    <input type="time" class="form-input" name="waktuBerangkat" id="editWaktuBerangkat">
                </div>
                <div class="form-group">
                    <label>Waktu Tiba</label>
                    <input type="time" class="form-input" name="waktuTiba" id="editWaktuTiba">
                </div>
            </div>
            <div class="form-group">
                <label>Biaya (Rp)</label>
                <input type="number" class="form-input" name="biaya" id="editBiaya" placeholder="0" required>
            </div>
            <div class="form-group">
                <label>Catatan</label>
                <textarea class="form-input" name="catatan" id="editCatatan" rows="2"></textarea>
            </div>`;
        
        setTimeout(() => {
            document.getElementById('editWaktuBerangkat').value = dataset.waktuBerangkat || '';
            document.getElementById('editWaktuTiba').value = dataset.waktuTiba || '';
            document.getElementById('editBiaya').value = dataset.biaya || '';
            document.getElementById('editCatatan').value = dataset.catatan || '';
        }, 0);

    } else if (type === 'accommodation') {
        title.innerHTML = '<i class="fas fa-hotel"></i> Edit Penginapan';
        form.action = `/itinerary/${itinId}/day/${dayId}/accommodation/${id}/edit`;
        body.innerHTML = `
            <p class="form-section-title"><i class="fas fa-hotel"></i> Detail Penginapan</p>
            <div class="form-group">
                <label>Pilih Akomodasi</label>
                <select class="form-input" name="accommodationId" id="editAccommodationId" required>
                    <option value="">-- Pilih Akomodasi --</option>
                    ${buildAccommodationOptions(dataset.accommodationId)}
                </select>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Check-in</label>
                    <input type="datetime-local" class="form-input" name="checkinTime" id="editCheckinTime">
                </div>
                <div class="form-group">
                    <label>Check-out</label>
                    <input type="datetime-local" class="form-input" name="checkoutTime" id="editCheckoutTime">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Jumlah Malam</label>
                    <input type="number" class="form-input" name="malam" id="editMalam" placeholder="1">
                </div>
                <div class="form-group">
                    <label>Biaya (Rp)</label>
                    <input type="number" class="form-input" name="biaya" id="editBiaya" placeholder="0" required>
                </div>
            </div>
            <div class="form-group">
                <label>Catatan</label>
                <textarea class="form-input" name="catatan" id="editCatatan" rows="2"></textarea>
            </div>`;
        
        setTimeout(() => {
            document.getElementById('editCheckinTime').value = dataset.checkin || '';
            document.getElementById('editCheckoutTime').value = dataset.checkout || '';
            document.getElementById('editMalam').value = dataset.malam || '';
            document.getElementById('editBiaya').value = dataset.biaya || '';
            document.getElementById('editCatatan').value = dataset.catatan || '';
        }, 0);

    } else if (type === 'destinasi') {
        title.innerHTML = '<i class="fas fa-umbrella-beach"></i> Edit Destinasi';
        form.action = `/itinerary/${itinId}/day/${dayId}/destinasi/${id}/edit`;
        body.innerHTML = `
            <p class="form-section-title"><i class="fas fa-umbrella-beach"></i> Detail Destinasi</p>
            <div class="form-group">
                <label>Pilih Destinasi</label>
                <select class="form-input" name="destinasiId" id="editDestinasiId" required>
                    <option value="">-- Pilih Destinasi --</option>
                    ${buildDestinasiOptions(dataset.destinasiId)}
                </select>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Waktu (HH:mm)</label>
                    <input type="time" class="form-input" name="waktu" id="editWaktuDestinasi">
                </div>
                <div class="form-group">
                    <label>Durasi (menit)</label>
                    <input type="number" class="form-input" name="durasiMenit" id="editDurasiMenit" placeholder="60">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Biaya (Rp)</label>
                    <input type="number" class="form-input" name="biaya" id="editBiaya" placeholder="0" required>
                </div>
            </div>
            <div class="form-group">
                <label>Catatan</label>
                <textarea class="form-input" name="catatan" id="editCatatan" rows="2"></textarea>
            </div>`;
        
        setTimeout(() => {
            document.getElementById('editWaktuDestinasi').value = dataset.waktu || '';
            document.getElementById('editDurasiMenit').value = dataset.durasi || '';
            document.getElementById('editBiaya').value = dataset.biaya || '';
            document.getElementById('editCatatan').value = dataset.catatan || '';
        }, 0);
    }
    openModal('modalEditActivity');
}

// ===== BUILD DROPDOWN OPTIONS =====
function buildTransportOptions(selectedId = '') {
    if (!allTransport || allTransport.length === 0) {
        return '<option value="">-- Data transport tidak tersedia --</option>';
    }
    return allTransport.map(t => 
        `<option value="${t.transportId}" ${t.transportId == selectedId ? 'selected' : ''}>${t.jenis} - ${t.provider}</option>`
    ).join('');
}

function buildAccommodationOptions(selectedId = '') {
    if (!allAccommodation || allAccommodation.length === 0) {
        return '<option value="">-- Data akomodasi tidak tersedia --</option>';
    }
    return allAccommodation.map(a => 
        `<option value="${a.accommodationId}" ${a.accommodationId == selectedId ? 'selected' : ''}>${a.nama} - ${a.lokasi}</option>`
    ).join('');
}

function buildDestinasiOptions(selectedId = '') {
    if (!allDestinasi || allDestinasi.length === 0) {
        return '<option value="">-- Data destinasi tidak tersedia --</option>';
    }
    return allDestinasi.map(d => 
        `<option value="${d.destinasiId}" ${d.destinasiId == selectedId ? 'selected' : ''}>${d.nama} - ${d.lokasi}</option>`
    ).join('');
}

// ===== SORT ACTIVITY BY TIME =====
function sortActivitiesByTime() {
    document.querySelectorAll('.activity-list').forEach(list => {
        const items = Array.from(list.children).filter(el => el.classList.contains('activity-item'));

        items.sort((a, b) => {
            const timeA = a.querySelector('.activity-time')?.textContent.trim() || '-';
            const timeB = b.querySelector('.activity-time')?.textContent.trim() || '-';

            if (timeA === '-' && timeB === '-') return 0;
            if (timeA === '-') return 1;
            if (timeB === '-') return -1;

            return timeA.localeCompare(timeB);
        });

        items.forEach(item => list.appendChild(item));
        list.classList.add('sorted');
    });
}

document.addEventListener('DOMContentLoaded', sortActivitiesByTime);