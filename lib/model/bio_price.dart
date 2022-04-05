
class BioPrice {
  String? name;
  double price = 0.0;

  Map<String, dynamic> toJson() => {
    "name": this.name,
    "price": this.price
  };
}
