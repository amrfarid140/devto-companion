import kotlinx.coroutines.flow.FlowCollector

class CocoaFlowCollector<T>(val completion: (T) -> Unit): FlowCollector<T> {
    override suspend fun emit(value: T) {
        completion(value)
    }
}