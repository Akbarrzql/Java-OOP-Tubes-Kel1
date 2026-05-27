document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const jenisFilter = document.getElementById('jenisFilter');
    const hargaSort = document.getElementById('hargaSort');
    const container = document.getElementById('transportContainer');

    // 1. FUNGSI UTAMA FILTER & SEARCH
    function applyFilters() {
        const searchValue = searchInput.value.toLowerCase();
        const selectedJenis = jenisFilter.value;
        const cards = document.querySelectorAll('.transport-card');

        cards.forEach(card => {
            const provider = card.getAttribute('data-provider');
            const jenis = card.getAttribute('data-jenis');

            let matchJenis = false;
            if (selectedJenis === "Semua Jenis") matchJenis = true;
            else if (selectedJenis === "Pesawat" && jenis === "Flight") matchJenis = true;
            else if (selectedJenis === "Kereta" && jenis === "Train") matchJenis = true;
            else if (selectedJenis === "Bus" && jenis === "Bus") matchJenis = true;

            const matchSearch = provider.includes(searchValue);

            if (matchJenis && matchSearch) {
                card.style.setProperty('display', 'flex', 'important');
            } else {
                card.style.setProperty('display', 'none', 'important');
            }
        });
    }

    // 2. FUNGSI SORTING HARGA (TERMURAH / TERTINGGI)
    function applySorting() {
        const cards = Array.from(document.querySelectorAll('.transport-card'));
        const sortValue = hargaSort.value;

        cards.sort((a, b) => {
            const hargaA = parseFloat(a.getAttribute('data-harga'));
            const hargaB = parseFloat(b.getAttribute('data-harga'));

            return sortValue === 'Termurah' ? hargaA - hargaB : hargaB - hargaA;
        });

        // Susun ulang posisi DOM elemen di HTML container
        cards.forEach(card => container.appendChild(card));
    }

    // Pasang Event Listeners
    searchInput.addEventListener('input', applyFilters);
    jenisFilter.addEventListener('change', applyFilters);
    hargaSort.addEventListener('change', applySorting);
});

// FUNGSI AKSI SAAT TRAVELER KLIK TOMBOL PILIH
function pilihTransportasi(id) {
    alert("Moda transportasi dengan ID " + id + " berhasil dipilih! Data ID ini siap diintegrasikan ke modul itinerary milik Akbar atau Nafz.");
}