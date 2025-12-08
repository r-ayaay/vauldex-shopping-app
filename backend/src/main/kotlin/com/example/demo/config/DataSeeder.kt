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
            val product1 = productRepository.save(Product(name = "GMK67 65%", price = 3833.40, description = "Hot-swappable gasket mounted per-key RGB "))
            productImageRepository.save(ProductImage(product = product1, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_2000/f_auto/v1/api-images/products/gmk-67-keyboard/casings/Black_Casing_h9xmqq"))
            productImageRepository.save(ProductImage(product = product1, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/gmk-67-keyboard/DSC00840_lb8m3h"))
            productImageRepository.save(ProductImage(product = product1, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/gmk-67-keyboard/DSC00850_xynq9a"))
            productImageRepository.save(ProductImage(product = product1, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/gmk-67-keyboard/DSC00847_dksy4m"))

            val product5 = productRepository.save(Product(name = "GMK104 100%", price = 4246.29, description = "Wireless Mechanical Keyboard"))
            productImageRepository.save(ProductImage(product = product5, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/gmk-104-keyboard/black-gmk-104-mechanical-keyboard_jqvac5"))
            productImageRepository.save(ProductImage(product = product5, url = ""))

            // Add more products as needed
            val product2 = productRepository.save(Product(name = "Wuque Studio Morandi", price = 456.54, description = "Thocky Linear Switches"))
            productImageRepository.save(ProductImage(product = product2, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/wuque-studios-morandi-switches/wuque-studios-morandi-switch_alia8h"))
            productImageRepository.save(ProductImage(product = product2, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/wuque-studios-morandi-switches/DSC05956_mddfjv"))
            productImageRepository.save(ProductImage(product = product2, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/wuque-studios-morandi-switches/DSC05959_accacs"))
            productImageRepository.save(ProductImage(product = product2, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/wuque-studios-morandi-switches/DSC05966_zkod6f"))

            val product3 = productRepository.save(Product(name = "HMX Frog", price = 552.09, description = "Tactile Switches, Ribbit Ribbit"))
            productImageRepository.save(ProductImage(product = product3, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1020/f_auto/v1/api-images/products/hmx-frog-switches/hmx-frog-tactile-transparent_n43gqj"))
            productImageRepository.save(ProductImage(product = product3, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/hmx-frog-switches/DSC05388_hia5pk"))

            val product4 = productRepository.save(Product(name = "Keyboard Switch Tester Pack V2", price = 1061.13, description = "9 Switches, acrylic base, and PBT keycaps"))
            productImageRepository.save(ProductImage(product = product4, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_280/f_auto/v1/api-images/products/keyboard-switch-sample-packs/v2/linear-keyboard-switch-sampler-pack_sifhjz"))
            productImageRepository.save(ProductImage(product = product4, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/keyboard-switch-sample-packs/v2/DSC00390_blmtrr"))

            val product6 = productRepository.save(Product(name = "Capybara", price = 552.09, description = "Thocky Linear Switches"))
            productImageRepository.save(ProductImage(product = product6, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/capybara-switches/capybara-linear-switches_k0nusq"))
            productImageRepository.save(ProductImage(product = product6, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/capybara-switches/capybara-switch-features_dtlsip"))


            val product7 = productRepository.save(Product(name = "Aluminum Keycap Full Set", price = 25952.59, description = "You must be ballin"))
            productImageRepository.save(ProductImage(product = product7, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_280/f_auto/v1/api-images/products/gateron-metal-keycaps/gateron-metal-keycaps_qxl3ch"))
            productImageRepository.save(ProductImage(product = product7, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/gateron-metal-keycaps/DSC00792_vdcxwe"))

            val product8 = productRepository.save(Product(name = "Attack on Titan PBT Keycaps", price = 4423.25, description = "Shinzuo Sasageyo!!!"))
            productImageRepository.save(ProductImage(product = product8, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_280/f_auto/v1/api-images/products/attack-on-titan-pbt/attack-on-titan-pbt-keycaps_svicaf"))
            productImageRepository.save(ProductImage(product = product8, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/attack-on-titan-pbt/DSC03315_x2wl3p"))


            val product9 = productRepository.save(Product(name = "Pikarar x Hipyo Tech V2 Desk Mat", price = 1768.95, description = "Designed by Pikarar in collaboration with Hipyo Tech"))
            productImageRepository.save(ProductImage(product = product9, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/pikarar-hipyo-tech-mat/pikarar-hipyo-tech-desk-mat_xifufp"))
            productImageRepository.save(ProductImage(product = product9, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/pikarar-hipyo-tech-mat/DSC07039_ttxtsh"))


            val product10 = productRepository.save(Product(name = "Sea Dreams Desk Mat", price = 1202.69, description = "Idk what a whale says. \"Woooo\"?"))
            productImageRepository.save(ProductImage(product = product10, url = "https://res.cloudinary.com/kineticlabs/image/upload/q_auto/c_fit,w_1500/f_auto/v1/api-images/products/sea-dreams-mat/DSC06184-Edit-plp_lkwe5o"))

            val product11 = productRepository.save(Product(name = "Yamaha P45", price = 36900.00, description = "88-Key Portable Digital Piano from Ya- WAIT A MINUTE"))
            productImageRepository.save(ProductImage(product = product11, url = "https://www.jbmusic.com.ph/__resources/webdata/images/product-gallery/21071_7145.jpg"))


//            val productN = productRepository.save(Product(name = "", price = 0.0, description = ""))
//            productImageRepository.save(ProductImage(product = productN, url = ""))
//            productImageRepository.save(ProductImage(product = productN, url = ""))


        }
    }
}
