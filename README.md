# java-convenience-store-precourse

## 목차

[1. 기능 요구 사항](#기능-요구-사항)  
[2. 입출력 요구 사항](#입출력-요구-사항)  
[3. 내가 생각한 기능](#내가-생각한-기능)  
[4. 구현 순서](#구현-순서)  
[5. 예외 처리](#예외)

### 목표

- 객체지향적 설계
- 객체간 협력
- MVC 패턴 적용
- 역할 분리

### 역할 분리

- 컨트롤러: 기능의 흐름 담당
- 서비스: 도메인 데이터 변환
- 도메인: 모델 역할로 데이터 보관, 관리, 계산 등의 비즈니스 로직 실행
- DTO: 출력을 위한 데이터 전달 객체
- 뷰: 입력 및 출력    
  <br>
- 편의점: 상품들과 프로모션 보관, 검사
- 상품: 재고, 프로모션 상태 보관 및 검사, 재고 관리
- 프로모션: 프로모션 적용 가능 유무 파악, 프로모션 관련 계산 진행
- 주문: 주문 아이템들 정보 보관, 관리
- 주문 아이템: 상품에 관한 주문 정보 보관, 관리, 계산 실행
- 영수증: 전체 주문 데이터 보관, 전체 주문에 관한 계산 실행

## 기능 요구 사항

구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.

- [x] 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
    - [x] 총구매액 = 상품별 가격 * 수량
    - [x] 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출
- [x] 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- [x] 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.
- [x] 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.

### 재고 관리

- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 상품 수량 -= 결제 수량

### 프로모션 할인

- [x] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- [x] 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
- [x] 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
- [x] 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- [x] 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

### 멤버십 할인

- [x] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- [x] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- [x] 멤버십 할인의 최대 한도는 8,000원이다.

### 영수증 출력

- [x] 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- 영수증 항목은 아래와 같다.
    - 구매 상품 내역: 구매한 상품명, 수량, 가격
    - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
    - 금액 정보
        - 총구매액: 구매한 상품의 총 수량과 총 금액
        - 행사할인: 프로모션에 의해 할인된 금액
        - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
        - 내실돈: 최종 결제 금액
- 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

## 입출력 요구 사항

### 입력

- [x] 구현에 필요한 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다.
    - src/main/resources/products.md과 src/main/resources/promotions.md 파일을 이용한다.
    - 두 파일 모두 내용의 형식을 유지한다면 값은 수정할 수 있다.
- [x] 구매할 상품과 수량을 입력 받는다. 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분한다.

```text
[콜라-10],[사이다-3]
```

- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
    - Y: 증정 받을 수 있는 상품을 추가한다.
    - N: 증정 받을 수 있는 상품을 추가하지 않는다.

- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
    - Y: 일부 수량에 대해 정가로 결제한다.
    - N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.

- [x] 멤버십 할인 적용 여부를 입력 받는다.
    - Y: 멤버십 할인을 적용한다.
    - N: 멤버십 할인을 적용하지 않는다.

- [x] 추가 구매 여부를 입력 받는다.
    - Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
    - N: 구매를 종료한다.

### 출력

- [x] 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다. 만약 재고가 0개라면 재고 없음을 출력한다.

```text
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
```

- [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력한다.

```text
현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
```

- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.

```text
현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
```

- [x] 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력한다.

```text
멤버십 할인을 받으시겠습니까? (Y/N)
```

- [x] 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.

```text
==============W 편의점================
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
=============증	정===============
콜라		1
====================================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000
```

- [x] 추가 구매 여부를 확인하기 위해 안내 문구를 출력한다.

```text
감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
```

- [x] 사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
    - 구매할 상품과 수량 형식이 올바르지 않은 경우: [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
    - 존재하지 않는 상품을 입력한 경우: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
    - 구매 수량이 재고 수량을 초과한 경우: [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
    - 기타 잘못된 입력의 경우: [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.

## 내가 생각한 기능

1. **주문시 동일한 상품 중복 주문**
    1. 먼저 들어온 주문 아이템에 개수 추가
    2. \[콜라-1],\[사이다-2],\[콜라-2]로 주문이 들어오면 콜라 3개, 사이다 2개로 처리
2. **프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.**
    1. 프로모션 적용 안 되는 개수를 주문해도 프로모션 재고에서 차감
    2. 2+1 상품을 1개만 주문해도 프로모션 재고에서 차감
3. **프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제 안 하고 그만큼 제외**
    1. 제외하면 어떤 상품을 몇 개 제외했는지 안내 문구 출력
        1. 프로모션이 적용되지 않는 {상품 이름} {해당 상품 주문 개수}개를 취소했습니다.
    2. 만약 1+1인 프로모션 재고가 1개고, 1개를 주문하면 프로모션 혜택 적용 불가 안내 출력
        1. 현재 {주문 상품} 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?
        2. Y: 프로모션이 적용되지 않으므로 멤버십 할인은 가능, 프로모션 재고에서 1개 차감
        3. N: 주문에서 해당 주문 아이템 제외
    3. 제외한 상품의 개수가 주문 개수라면 해당 상품 제외
        1. 만약 모든 주문이 취소됐다면 재주문 여부 안내 문구 출력

## 구현 순서

1. [x] 환영 인사 출력
2. [x] 파일 읽기
3. [x] 편의점 상품으로 변환
4. [x] 상품명, 가격, 프로모션 이름, 재고 안내
    1. [x] 재고가 0개라면 재고 없음으로 출력
5. [x] 구매 상품과 수량 입력 (예시: "\[사이다-2],\[감자칩-1]")
    1. [x] 재고 수량 고려하여 결제 가능 여부 확인
        1. [x] 불가능 시 문구 출력 후 재입력
    2. [x] 예외 처리
        1. [x] 같은 상품 여러 번 주문 들어오면 하나로 합침
6. [x] 프로모션 적용
    1. [x] 가능 여부 판별
        1. [x] 프로모션 기간 내인지 확인
        2. [x] 프로로션 재고가 있는지 확인
        3. [x] 프로모션 재고 우선 차감. 부족 시 일반 재고 사용
    2. [x] 혜택 안내 메시지 출력
        1. [x] 프로모션 적용 가능 상품보다 적게 가져온 경우, 추가로 가져오면 혜택 가능 문구 출력
            1. 프로모션이 들어가는 경우와 같은 경우에만 안내
                1. 예시) 2 + 1일 경우 1개 가져오면 안내 X, 2개 가져오면 안내 O
                2. 2+1인 상품을 7개 가져오면 6개만 적용됨. 한 개는 혜택 문구 없이 일반 결제 진행
        2. [x] 프로모션 재고 부족으로 일반 재고 사용 시, 일부 수량에 대해 정가로 결제하겠냐는 문구 출력
            1. [x] 그대로 결제(Y): 남은 수량은 정가로 결제
            2. [x] 결제 취소(N): 적용되지 않는 수량만 취소하겠냐는 문구 출력
                1. [x] 적용 안된 수량만 취소(Y): 프로모션 적용된 수량만큼만 결제
                2. [x] X: 해당 상품 전부 취소
    3. [x] 예외 처리
7. [x] 멤버십 할인
    1. [x] 적용 여부 판별
    2. [x] 프로모션 미적용 금액의 30% 할인
    3. [x] 프로모션 적용 후 남은 금액 할인 적용
    4. [x] 최대 한도 8000원
    5. [x] 예외 처리
8. [x] 영수증 출력
    1. [x] 구매 상품 내역 출력
    2. [x] 증정 상품 내역 출력
    3. [x] 금액 정보 출력
        1. 총구매액
        2. 프로모션 할인 금액
        3. 멤버십 할인 금액
        4. 최종 결제 금액
9. [x] 추가 구매 여부 안내 문구 출력
    1. [x] 반복
    2. [x] 예외 처리

## 예외

- 파일 읽기

| 상황    | 예시             | 처리 방법 |
|-------|----------------|-------|
| 없는 파일 | "notFound.txt" | 파일 에러 |

- 프로모션 리스트로 변환

| 상황       | 예시                                                    | 처리 방법 |
|----------|-------------------------------------------------------|-------|
| 필드 개수 에러 | 6가지 필드가 존재하지 않음                                       | 파일 에러 |
| 필드 에러    | "탄산2+1,a,b,2024-11-01,2024-+11-03" 또는 "탄산2+1,2,1,a,b" | 양식 에러 |

- 편의점 상품으로 변환

| 상황       | 예시                                       | 처리 방법 |
|----------|------------------------------------------|-------|
| 필드 개수 에러 | 5가지 필드가 존재하지 않음                          | 파일 에러 |
| 필드 에러    | "감자칩-1000-10-MD추천상품" 또는 "감자칩,a,b,MD추천상품" | 양식 에러 |

- 주문

| 상황             | 예시                                                                  | 처리 방법           |
|----------------|---------------------------------------------------------------------|-----------------|
| 주문 양식 에러       | - \[사이다-8,콜라-20]<br>- 사이다-10,콜라-20<br> - \[사이다,10]<br> -\[사이다-10-1] | 올바르지 않은 형식으로 입력 |
| 주문 아이템 양식 에러   | - \[사이다-8],\[콜라-a]                                                  | 올바르지 않은 형식으로 입력 |
| 존재하지 않는 주문 아이템 | \[스모어초콜릿-1]                                                         | 존재하지 않는 상품      |
| 주문 아이템 재고 초과   | 재고가 5개인 상품을 \[물-10]로 주문                                             | 재고 수량을 초과       |

- 프로모션, 멤버십

| 예외 상황                | 예시           | 처리 방법      |
|----------------------|--------------|------------|
| 프로모션 적용 가능한 상품 추가 질문 | a or ㅐ or "" | 올바르지 않은 형식 |
| 프로모션 재고 부족, 정가 결제 질문 | a or ㅒ or "" | 올바르지 않은 형식 |
| 멤버십 적용 질문            | a or ㅒ or "" | 올바르지 않은 형식 |

<div align="right">

[목차로](#목차)

</div>
