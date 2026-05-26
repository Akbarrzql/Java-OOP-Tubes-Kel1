function openAddModal() {
    document.getElementById('modalTitle').innerText = 'Tambah Transportasi Baru';
    document.getElementById('transportForm').reset();
    document.getElementById('formTransportId').value = ''; 
    
    const modal = document.getElementById('transportModal');
    modal.classList.remove('hidden');
    modal.classList.add('flex');
}

function closeTransportModal() {
    const modal = document.getElementById('transportModal');
    modal.classList.add('hidden');
    modal.classList.remove('flex');
}

function openEditModal(id) {
    document.getElementById('modalTitle').innerText = 'Perbarui Data Transportasi';
    
    fetch('/admin/transport/edit/' + id)
        .then(response => response.json())
        .then(data => {
            document.getElementById('formTransportId').value = data.transportId;
            document.getElementById('formJenis').value = data.jenis;
            document.getElementById('formProvider').value = data.provider;
            document.getElementById('formJadwal').value = data.jadwal;
            document.getElementById('formHarga').value = data.harga;
            
            const modal = document.getElementById('transportModal');
            modal.classList.remove('hidden');
            modal.classList.add('flex');
        })
        .catch(error => {
            console.error('Gagal mengambil data:', error);
            alert('Gagal memuat data master moda transportasi.');
        });
}