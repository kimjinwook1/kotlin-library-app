### 테스트 코드는 왜 필요한가

1. 개발 과정에서 문제를 미리 발견할 수 있다.
2. 기능 추가와 리팩토링을 안심하고 할 수 있다.
3. 빠른 시간 내 코드의 동작 방식과 결과를 확인할 수 있다.
4. 좋은 테스트 코드를 작성하려 하다보면, 자연스럽게 좋은 코드가 만들어 진다.
5. 잘 작성한 테스트는 문서 역할을 한다.(코드리뷰를 돕는다)

### 계산기 요구사항

1. 계산기는 정수만을 취급한다.
2. 계산기가 생성될 때 숫자를 1개 받는다.
3. 최초 숫자가 기록된 이후에는 연산자 함수를 통해 숫자를 받아 지속적으로 계산한다.

### Setter

생성자 안의 var 프로퍼티

```kotlin
@Entity
class User(
    var name: Stirng,

    val age: Int?,
) {
    fun updateName(name: String) {
        this.name = name
    }
}
```

- setter 대신 좋은 이름의 함수를 사용하는 것이 훨씬 clean하다.
- 하지만 name에 대한 setter는 public이기 떄문에 유저 이름 업데이트 기능에서 setter를 **사용할 수도** 있다.

- public getter는 필요하기 때문에 setter만 private하게 만드는 것이 최선이다.

방법 1. backing property 사용하기

```kotlin
class User(
    private var _name: String
) {
    val name: String
        get() = this._name
}
```

방법 2. custom setter 이용하기

```kotlin
class User(
    name: String
) {
    var name = name
        private set
}
```

- 하지만 두 방법 모두 프로퍼티가 많아지면 번거롭다.
- 그렇기 때문에 setter를 열어는 두지만 사용하지 않는 방법이 편하다.

### 생성자 안의 프로퍼티, 클래스 body 안의 프로퍼티

```kotlin
@Entity
class User(
    var name: String,

    val age: Int,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

}
```

- 꼭 primary constructor 안에 모든 프로퍼티를 넣어야 할까?

```kotlin
@Entity
class User(
    var name: String,

    val age: Int,

    ) {

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
```
-> 위의 코드도 정상 작동한다.

1. 모든 프로퍼티를 생성자에 넣거나
2. 프로퍼티를 생성자 혹은 클래스 body 안에 구분해서 넣을 때 명확한 기준이 있거

### JPA와 data class
- Entity 클래스는 data class를 피하는 것이 좋다.
-> equals, hashCode, toString 모두 JPA Entity와는 100% 어울리지 않는 메서드이기 때문이다.

### TIP
- Entity가 생성되는 로직을 찾고 싶다면 constructor 지시어를 명시적으로 작성하고 추적하자.

````kotlin
@Entity
class User constructor( // <- constructor 지시어를 사용해라

    var name: String,
    val age: Int?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}
````

### Controller를 구분하는 3가지 기준
**1. 화면에서 사용되는 API끼리 모아 둔다.**
- 장점
  - 화면에서 어떤 API가 사용되는지 한 눈에 알기 쉽다.
- 단점
  - 한 API가 여러 홤녀에서 사용되면 위치가 애매하다.
  - 서버 코드가 화면에 종속적이다.

**2. 동일한 도메인끼리 API를 모아 둔다.**
- 장점
  - 화면 위치와 무관하게 서버 코드는 변경되지 않아도 된다.
  - 비슷한 API끼리 모이게 되며 코드의 위치를 예측할 수 있다.
- 단점
  - 이 API가 어디서 사용되는지 서버 코드만 보고 알기는 어렵다.

**3. 1 API 1 Controller를 사용한다.**
- 장점
  - 화면 위치와 무관하게 서버 코드는 변경되지 않아도 된다.
- 단점
  - 이 API가 어디서 사용되는지 서버 코드만 보고 알기는 어렵다.
