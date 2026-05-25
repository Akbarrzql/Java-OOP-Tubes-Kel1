const transportasi = [

    {
        provider: "Garuda Indonesia",
        jenis: "Pesawat",
        icon: "fa-plane",
        asal: "Jakarta",
        tujuan: "Bali",
        harga: 1450000
    },

    {
        provider: "KAI Executive",
        jenis: "Kereta",
        icon: "fa-train",
        asal: "Jakarta",
        tujuan: "Surabaya",
        harga: 650000
    },

    {
        provider: "Sinar Jaya",
        jenis: "Bus",
        icon: "fa-bus",
        asal: "Jakarta",
        tujuan: "Yogyakarta",
        harga: 450000
    }
];

const container =
    document.getElementById("transportContainer");

function renderData(data) {

    container.innerHTML = "";

    data.forEach(item => {

        container.innerHTML += `

        <div class="transport-card">

            <div class="flex items-center gap-3 mb-6">

                <i class="fas ${item.icon} text-2xl"></i>

                <div>

                    <h3 class="font-bold text-xl">
                        ${item.provider}
                    </h3>

                    <p class="text-gray-500">
                        ${item.jenis}
                    </p>

                </div>

            </div>

            <div class="mb-4">

                <p>
                    ${item.asal}
                    →
                    ${item.tujuan}
                </p>

            </div>

            <div class="text-2xl font-bold">

                Rp ${item.harga.toLocaleString("id-ID")}

            </div>

            <button
                onclick="pilihTransport('${item.provider}')"

                class="transport-button"
            >

                Pilih Transport

            </button>

        </div>
        `;
    });
}

function pilihTransport(provider) {

    alert(
        "Transport dipilih : " + provider
    );
}

document.getElementById("searchInput")
    .addEventListener("keyup", function () {

        const keyword =
            this.value.toLowerCase();

        const hasil =
            transportasi.filter(item =>
                item.provider
                    .toLowerCase()
                    .includes(keyword)
            );

        renderData(hasil);
    });

document.getElementById("jenisFilter")
    .addEventListener("change", function () {

        const jenis = this.value;

        if (jenis === "Semua Jenis") {

            renderData(transportasi);

            return;
        }

        const hasil =
            transportasi.filter(item =>
                item.jenis === jenis
            );

        renderData(hasil);
    });

document.getElementById("hargaSort")
    .addEventListener("change", function () {

        const sorted = [...transportasi];

        if (this.value === "Harga Termurah") {

            sorted.sort((a,b)=>
                a.harga - b.harga
            );

        } else {

            sorted.sort((a,b)=>
                b.harga - a.harga
            );
        }

        renderData(sorted);
    });

renderData(transportasi);