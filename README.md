# Android Studio를 활용한 운동기록 어플리케이션의 제작
### 목차 :book:
1. 프로젝트 이름

2. 프로젝트 일정

3. 기술 스택

4. Room 라이브러리를 활용한 데이터베이스의 구현

5. 프로젝트 데모

-----
### 1. 프로젝트 이름 :memo:
<img src="https://user-images.githubusercontent.com/61148914/104551372-1c8e0380-567a-11eb-829f-27914a2121b6.png" width="25%">

-----
### 2. 프로젝트 일정 :calendar:
* **2020.12.23 :** Android Studio 설치 및 기본 환경설정

* **2020.12.24 ~ 2020.12.25 :** 키, 몸무게에 따른 BMI 수치 및 몸상태 출력 기능 구현

* **2020.12.26 :** Room 라이브러리를 활용한 데이터베이스의 구현

* **2020.12.27 ~ 2020.12.28 :** 날짜별 운동시간에 대한 데이터 처리 로직(삽입, 삭제, 수정) 구현

* **2020.12.29 ~ 2021.01.03 :** 설정 메뉴에 대한 UI 구성 및 기능 구현

* **2021.01.04 ~ 2021.01.07 :** MPAndroidChart 라이브러리를 활용한 그래프 구현 및 테스트

* **2021.01.08 :** 그래프 출력에 대한 오류 수정

* **2021.01.09 :** 최종 테스트

* **2021.01.10 :** UI 보완 및 마무리

-----
### 3. 기술 스택 :computer:
* 활용 IDE
```
- Android Studio
```
* 활용 언어
```
- Java
```
* 활용 라이브러리
```
- Room
- MPAndroidChart
```

-----
### 4. Room 라이브러리를 활용한 데이터베이스의 구현 :floppy_disk:
**1) Room 이란?**   
ㆍ 안드로이드 앱에서 SQLite 데이터베이스를 쉽고 편리하게 사용할 수 있도록 하는 기능이다. 사용자의 데이터를 로컬 데이터베이스에 저장하여 기기가 네트워크에 접근할 수 없을 때도 사용자가 여전히 콘텐츠를 탐색할 수 있다. 

**2) Room의 구성요소 (Database, Entity, Dao)**
```java
@Entity
public class UserData {
   
    @PrimaryKey
    private int date;
    private int time;


    public UserData(int date, int time) {
        this.date = date;
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "date=" + this.date +
                ", time=" + this.time +
                '}' + "\n";
    }
}
```
ㆍ Room을 구성하는 3가지 주요요소 중 하나인 Enitity에 관한 클래스이다.   
ㆍ 데이터베이스에서 테이블 역할을 수행하며, 데이터베이스에 저장되는 데이터를 정의한 클래스이다.   
ㆍ "@Entity" 어노테이션을 클래스 상단에 작성함으로써, Entity역할을 하는 클래스임을 명시한다.   
ㆍ "@PrimaryKey" 어노테이션으로 기본 키 값을 지정할 수 있으며, 기본 키는 중복될 수 없다.   
<br/>
```java
@Dao
public interface UserDataDao {
  
    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    @Query("DELETE FROM UserData")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void put(UserData userData);

    @Update
    void update(UserData userDate);

    @Delete
    void delete(UserData userData);
}
```
ㆍ Room을 구성하는 3가지 주요요소 중 하나인 DAO에 관한 인터페이스이다.   
ㆍ 데이터베이스에 접근하여 수행할 작업을 메소드 형태로 정의하였다.   
<br/>
```java
@androidx.room.Database(entities = {UserData.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract UserDataDao userDataDao();
}
```
ㆍ Room을 구성하는 3가지 주요요소 중 하나인 Database에 관한 인터페이스이다.   
ㆍ 데이터베이스를 새롭게 생성하거나 버전을 관리하기위한 클래스이다.   
ㆍ 해당 인터페이스는 RoomDatabase 클래스를 상속받는 추상 클래스여야 한다.   
ㆍ "@Database" 어노테이션을 통해 Database 역할을 하는 인터페이스임을 명시해야 하며, 어노테이션 안에 해당 데이터베이스와 관련된 Entity 리스트를 포함해야 한다.   
<br/>


