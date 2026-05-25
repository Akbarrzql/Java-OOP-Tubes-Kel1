const transportasi = [

    {
        jenis: "Flight",
        provider: "Garuda Indonesia",
        jadwal: "08:00 - 10:00",
        harga: 1500000,
        icon: "fa-plane"
    },

    {
        jenis: "Train",
        provider: "KAI Executive",
        jadwal: "13:00 - 18:30",
        harga: 650000,
        icon: "fa-train"
    },

    {
        jenis: "Bus",
        provider: "Sinar Jaya",
        jadwal: "20:00 - 05:00",
        harga: 450000,
        icon: "fa-bus-alt"
    }
];

const tableBody =
    document.getElementById("tableBody");


function rupiah(angka) {

    return angka.toLocaleString("id-ID");
}


function renderTable() {

    tableBody.innerHTML = "";

    transportasi.forEach((item, index) => {

        tableBody.innerHTML += `

            <tr class="table-row">

                <td class="p-4">

                    <div class="
                        flex
                        items-center
                        gap-3
                    ">

                        <div class="
                            w-10 h-10
                            rounded-full
                            bg-gray-100
                            flex
                            items-center
                            justify-center
                        ">

                            <i class="
                                fas
                                ${item.icon}
                            "></i>

                        </div>

                        ${item.jenis}

                    </div>

                </td>

                <td class="p-4">
                    ${item.provider}
                </td>

                <td class="p-4">
                    ${item.jadwal}
                </td>

                <td class="p-4">
                    Rp ${rupiah(item.harga)}
                </td>

                <td class="p-4 text-right">

                    <button
                        onclick="editData(${index})"
                        class="
                            border
                            px-3 py-1
                            rounded
                            mr-2
                        ">
                        Edit
                    </button>

                    <button
                        onclick="hapusData(${index})"
                        class="
                            border
                            border-red-400
                            text-red-500
                            px-3 py-1
                            rounded
                        ">
                        Hapus
                    </button>

                </td>

            </tr>
        `;
    });
}


function editData(index) {

    alert(
        "Edit : "
        + transportasi[index].provider
    );
}


function hapusData(index) {

    const konfirmasi =
        confirm("Yakin hapus data?");

    if (konfirmasi) {

        transportasi.splice(index, 1);

        renderTable();
    }
}


document.getElementById("btnTambah")
    .addEventListener("click", function () {

        alert("Tambah transportasi");
    });


renderTable();