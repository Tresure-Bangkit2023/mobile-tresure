package id.tresure.android.data.local

object PlaceData {
    private val placeData = arrayOf(
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
        arrayOf(
            "Bandung",
            "Ini Bandung",
            "https://asset.kompas.com/crops/nGq51Hv0XcV0hiPrn0wPDde0WbA=/0x0:0x0/750x500/data/photo/2021/09/28/61530b8121ee8.jpg",
            "Rp1.000.000"
        ),
    )

    val listPlace: ArrayList<Place>
        get() {
            val list = arrayListOf<Place>()
            for (data in placeData) {
                val place = Place()
                place.name = data[0]
                place.description = data[1]
                place.image = data[2]
                place.price = data[3]

                list.add(place)
            }
            return list
        }
}