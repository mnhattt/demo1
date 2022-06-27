import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.random.Random

class Cart {
    var products: MutableList<Product> = mutableListOf()
    fun addProduct(product: Product) {
        products.add(product)
    }

    var vouchers: MutableList<Voucher> = mutableListOf()
    fun addVoucher(voucher: Voucher) {
        vouchers.add(voucher)
    }

    var totalPrice: Double = 0.0
    var shippingFee: Double = Random.nextInt(70).toDouble()

    fun applyVoucher() {
        for (voucher in vouchers) {
            voucher.use(this)
        }
    }

    fun sumProductPrice(): Double {
        return products.sumOf { it.price }
    }

    fun getFinalPrice(): Double {
        applyVoucher()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(totalPrice + shippingFee).toDouble()
    }
}


interface Voucher {
    fun use(cart: Cart)
}

class FreeshipVoucher : Voucher {
    val maxFreeship: Double = 50.0

    override fun use(cart: Cart) {
        val newShippingFee = max(0.0, cart.shippingFee - maxFreeship)
        cart.shippingFee = newShippingFee
    }
}

class DiscountVoucher : Voucher {
    val discountPercent: Double = 5.0

    override fun use(cart: Cart) {
        if (cart.totalPrice == 0.0) {
            val sumPrice = cart.sumProductPrice()
            val newPrice = sumPrice * (100 - discountPercent) / 100
            cart.totalPrice = newPrice
        } else {
            val newPrice = cart.totalPrice * (100 - discountPercent) / 100
            cart.totalPrice = newPrice
        }
    }
}

class DirectDiscountVoucher : Voucher {
    val discountPrice: Double = 30.0

    override fun use(cart: Cart) {
        val sumPrice = cart.sumProductPrice()
        cart.totalPrice = sumPrice - discountPrice
    }
}

class Product(val price: Double)