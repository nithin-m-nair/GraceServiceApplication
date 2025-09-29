package com.horizon.service.serviceapplication.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.service.serviceapplication.Repository.Repo
import com.horizon.service.serviceapplication.model.Featured
import com.horizon.service.serviceapplication.model.FeaturedItemData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class PromoUiState {
    object Loading : PromoUiState()
    data class Success(val items: List<FeaturedItemData>) : PromoUiState()
    data class Error(val message: String) : PromoUiState()
}

class HomeViewModel(
    private val repository: Repo
) : ViewModel() {

    private val _promoState = MutableStateFlow<PromoUiState>(PromoUiState.Loading)
    val promoState: StateFlow<PromoUiState> = _promoState

    init {
        loadPromos()
    }

    private fun loadPromos() {
        viewModelScope.launch {
            try {
                val items = repository.getItems()
                _promoState.value = PromoUiState.Success(items.data)
            } catch (e: Exception) {
                _promoState.value = PromoUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
