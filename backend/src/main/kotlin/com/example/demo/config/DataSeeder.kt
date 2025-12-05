package com.example.demo.config

import com.example.demo.entity.Product
import com.example.demo.entity.ProductImage
import com.example.demo.repository.ProductImageRepository
import com.example.demo.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(
    private val productRepository: ProductRepository,
    private val productImageRepository: ProductImageRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (productRepository.count() == 0L) {
            val product1 = productRepository.save(Product(name = "Mechanical Keyboard", price = 129.99, description = "RGB mechanical keyboard"))
            productImageRepository.save(ProductImage(product = product1, url = "https://rkgamingstore.com/cdn/shop/files/R7575_WiredGamingKeyboard_QMKVIA_4.png"))
            productImageRepository.save(ProductImage(product = product1, url = "https://m.media-amazon.com/images/I/61hOhuiqrTL._AC_UF1000,1000_QL80_.jpg"))

            // Add more products as needed
            val product2 = productRepository.save(Product(name = "Linear Switches", price = 19.99, description = "Ohhhhh so creamy and smooth"))
            productImageRepository.save(ProductImage(product = product2, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/wuque-studios-morandi-switches/wuque-studios-morandi-switch_alia8h"))

            val product3 = productRepository.save(Product(name = "Tactile Switches", price = 19.99, description = "Woowie so clicky and clacky"))
            productImageRepository.save(ProductImage(product = product3, url = "https://ueeshop.ly200-cdn.com/u_file/UPAW/UPAW819/2408/07/products/e249901118.webp?x-oss-process=image/resize,m_lfit,h_1000,w_1000/quality,q_100"))


        }
    }
}
