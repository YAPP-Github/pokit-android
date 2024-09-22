package pokitmons.pokit.terms

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TermsViewModel() : ViewModel() {
    private val _termsState = MutableStateFlow(TermsState())
    val termsState: StateFlow<TermsState>
        get() = _termsState

    fun checkAllTerms() {
        when (_termsState.value.isAllChecked) {
            true -> _termsState.value = TermsState()
            false -> _termsState.value = _termsState.value.copy(
                isAllChecked = true,
                isPersonalDataChecked = true,
                isServiceChecked = true,
                isMarketingChecked = true
            )
        }
    }

    fun checkPersonalData() {
        _termsState.value = _termsState.value.copy(isPersonalDataChecked = !_termsState.value.isPersonalDataChecked)
        isAllCheck()
    }

    fun checkServiceTerm() {
        _termsState.value = _termsState.value.copy(isServiceChecked = !_termsState.value.isServiceChecked)
        isAllCheck()
    }

    fun checkMarketing() {
        _termsState.value = _termsState.value.copy(isMarketingChecked = !_termsState.value.isMarketingChecked)
        isAllCheck()
    }

    private fun isAllCheck() = with(_termsState.value) {
        _termsState.value = copy(isAllChecked = isMarketingChecked && isServiceChecked && isPersonalDataChecked)
    }
}
