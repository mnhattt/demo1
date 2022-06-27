fun main() {
    val p1 = Product(100.0)
    val p2 = Product(200.0)
    val p3 = Product(300.0)

    val cart = Cart()
    cart.addProduct(p1)
    cart.addProduct(p2)
    cart.addProduct(p3)

    cart.addVoucher(DirectDiscountVoucher())
    cart.addVoucher(DiscountVoucher())
    cart.addVoucher(FreeshipVoucher())

    println("product price ${cart.sumProductPrice()} - shipping fee: ${cart.shippingFee}")
    println("final total ${cart.getFinalPrice()}")
}