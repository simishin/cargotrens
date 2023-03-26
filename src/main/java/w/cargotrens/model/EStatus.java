/**
 * Статус Заказа
 */
package w.cargotrens.model;

public enum EStatus {
    PREP, //подготовка
    SHAPED, //сформирован
    CONVEYED, //передан в доставку (назначен водитель)
    DELIVERED; //доставлен
}