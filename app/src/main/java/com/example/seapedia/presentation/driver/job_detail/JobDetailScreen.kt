package com.example.seapedia.presentation.driver.job_detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.entities.Job
import com.example.seapedia.domain.entities.OrderItem
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.TimeFormatting
import com.example.seapedia.global.utils.TimeFormatting.toDisplayString
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.driver.job_detail.shimmer.JobDetailCardShimmer
import com.example.seapedia.ui.theme.Dimens
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import androidx.core.net.toUri
import com.example.seapedia.global.utils.MapLocation


@OptIn(ExperimentalTime::class)
@Composable
fun JobDetailScreen(
    navController: NavController,
    viewModel: JobDetailViewModel = hiltViewModel<JobDetailViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.triggerRefresh.collect {
            navController.previousBackStackEntry?.savedStateHandle?.set("refresh_job",true)
        }
    }
    val scrollState = rememberScrollState()
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(Dimens.InnerPadding)
                .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(val result = state.job){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = result.message)
            }
            is CommonState.Loading<*> -> {
                JobDetailCardShimmer()
            }
            is CommonState.Success -> {
                JobDetailCard(
                    job = result.data,
                    daySystem = state.daySystem ?: Clock.System.now(),
                    isLoading = state.isLoading,
                    onConfirmJob = viewModel::onConfirmJob,
                    onTakeJob = viewModel::onTakeJob
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun JobDetailCard(
    modifier: Modifier = Modifier,
    job: Job,
    daySystem: Instant,
    isLoading: Boolean,
    onTakeJob: () -> Unit = {},
    onConfirmJob: () -> Unit = {},
) {
    val context = LocalContext.current
    val order = job.order
    val titleText = when(order.status){
        OrderStatus.ON_WAY -> "Confirm Job"
        OrderStatus.WAITING_DRIVER -> "Take Job"
        else -> null
    }

    val currentJourney = when (order.status) {
        OrderStatus.WAITING_DRIVER,
        OrderStatus.RETURN -> "${order.store.address} → Store"

        OrderStatus.ON_WAY ->
            "${order.store.address} → ${order.orderAddress.receiverAddress}"

        else -> null
    }
    val currentJourneyAddress = when (order.status) {
        OrderStatus.WAITING_DRIVER,
        OrderStatus.RETURN -> MapLocation(
            latitude = order.store.latitude.toString(),
            longitude = order.store.longitude.toString()
        )

        OrderStatus.ON_WAY -> MapLocation(
            latitude = order.orderAddress.latitude,
            longitude = order.orderAddress.longitude
        )

        else -> null
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.InnerPadding),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Job #${job.id}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = job.createdAt.toDisplayString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                OrderStatusBadge(order.status)
            }
            HorizontalDivider()
            DetailItem(
                title = "Earning",
                value = Formatting().formatRupiah(job.earning.toString())
            )

            DetailItem(
                title = "Expired",
                value = job.expiredDate.toDisplayString(),
            )

            if (order.status == OrderStatus.ON_WAY) {
                DetailItem(
                    title = "Remaining Time",
                    value = TimeFormatting.formatOverdueTime(
                        instant = job.expiredDate,
                        daySystem = daySystem
                    )
                )
            }

            DetailItem(
                title = "Distance",
                value = "${order.distanceJourneyKm} KM"
            )

            currentJourney?.let {
                HorizontalDivider()
                Column {
                    Text(
                        text = "Current Journey",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            HorizontalDivider()
            Column {
                Text(
                    text = "Store Address",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    order.store.address,
                    style = MaterialTheme.typography.bodyMedium)
            }
            Column {
                Text(
                    text = "Delivery Address",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))
                Text(order.orderAddress.receiverName,style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text(order.orderAddress.receiverPhoneNumber,style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text(order.orderAddress.receiverAddress,style = MaterialTheme.typography.bodyMedium)
            }
            HorizontalDivider()
            Text(
                text = "Products",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                order.orderItems.forEach { item ->
                    ProductDetailItem(item)
                }
            }
            if (order.status == OrderStatus.ON_WAY || order.status == OrderStatus.WAITING_DRIVER || order.status == OrderStatus.RETURN) {
                if(order.status != OrderStatus.RETURN)
                    ButtonCustom(
                    title = titleText ?: "",
                    isNotLoading = !isLoading,
                    enabled = true,
                    onClick = {
                        if(order.status == OrderStatus.ON_WAY){
                            onConfirmJob()
                        }else{
                            onTakeJob()
                        }
                    }
                )
                ButtonCustom(
                    title = "See Location",
                    isNotLoading = !isLoading,
                    enabled = true,
                    onClick = {
                        val url = "https://www.google.com/maps/search/?api=1&query=${currentJourneyAddress!!.latitude},${currentJourneyAddress.longitude}"
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, url.toUri())
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun OrderStatusBadge(
    status: OrderStatus
) {
    val background = when (status) {
        OrderStatus.RETURN -> Color.Red.copy(.15f)
        OrderStatus.ON_WAY -> Color(0xFFFFC107).copy(.20f)
        OrderStatus.DONE -> Color.Green.copy(.15f)
        else -> MaterialTheme.colorScheme.primary.copy(.12f)
    }

    val textColor = when (status) {
        OrderStatus.RETURN -> Color.Red
        OrderStatus.ON_WAY -> Color(0xFF8A6D00)
        OrderStatus.DONE -> Color(0xFF2E7D32)
        else -> MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = Modifier
            .background(
                background,
                RoundedCornerShape(100)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = status.displayName,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProductDetailItem(
    item: OrderItem
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.product.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text("x${item.quantity}",
                style = MaterialTheme.typography.bodyMedium)
        }

        item.orderItemType.forEach { type ->

            Text(
                text = type.productType.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            type.productTypeItem.forEach {

                Text(
                    text = "• ${it.productTypeItemOrderItem.name}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = Formatting().formatRupiah(item.subTotal.toString()),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun DetailItem(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}