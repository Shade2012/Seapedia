package com.example.seapedia.presentation.seller.order.detail.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.seapedia.R
import com.example.seapedia.domain.entities.Driver
import com.example.seapedia.domain.entities.OrderAddress
import com.example.seapedia.domain.entities.OrderItem
import com.example.seapedia.domain.entities.Store
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun OrderAddressCard(
    store: Store,
    address: OrderAddress,
    driver: Driver?
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        if(driver != null)
            OrderDriverInformation(driver)
        OrderStoreAddressInformation(store)
        OrderAddressInformation(address)
    }
}

@Composable
fun OrderDriverInformation(
    driver: Driver?
    ) {
    driver?.let {
        driver ->
        Text("Driver Information", style = MaterialTheme.typography.titleMedium)
        OrderAddressDetail(driver.fullName,Icons.Default.Person)
    }
}

@Composable
fun OrderStoreAddressInformation(
    store: Store,
) {
    Text("Store Information", style = MaterialTheme.typography.titleMedium)
    OrderAddressDetail(store.name, Icons.Default.Person)
    OrderAddressDetail(store.phoneNumber, Icons.Default.Phone)
    OrderAddressDetail(store.address, ImageVector.vectorResource(R.drawable.store_icon))
}

@Composable
fun OrderAddressInformation(
    orderAddress: OrderAddress
) {
    Text("Customer Information", style = MaterialTheme.typography.titleMedium)
    OrderAddressDetail(orderAddress.receiverName, Icons.Default.Person)
    OrderAddressDetail(orderAddress.receiverPhoneNumber, Icons.Default.Phone)
    OrderAddressDetail(orderAddress.receiverAddress, ImageVector.vectorResource(R.drawable.store_icon))
}
@Composable
fun OrderAddressDetail(
    text: String,
    icon: ImageVector
) {
    Row (
        horizontalArrangement = Arrangement.Start
    ){
        IconCustom(
            icon = icon,
            contentDescription = "Icon Address",
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.padding(Dimens.SpacePadding))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}