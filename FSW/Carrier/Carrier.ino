#include <SoftwareSerial.h>
#include <RTClib.h>
#include <Wire.h>
#include <XBee.h>
#include <TinyGPS++.h>
#include <TinyGPS.h>
#include <MPU6050.h>
#include <Servo.h>
#include <String.h>
#define NUM_SAMPLES 10
char cmd1 ='F';
Servo esc;
const int buzzer = 10;          //Buzzer
int sum = 0;                    // sum of samples taken
unsigned char sample_count = 0; // current sample number
char climb=false;
char decent=false;
//char state= ('0','1','2','3','4','5','6');
unsigned int state = 0;
  
//gyro start
const int MPU_6050_addr = 0x68; 
const byte GYRO_CONFIG = 0x1B;
float MPU_6050_GyroX;
float MPU_6050_GyroY;
float MPU_6050_GyroZ;
//gyro end

//state start


//Objects

//gps start
static const int RXPin = 4, TXPin = 5;
static const uint32_t GPSBaud = 9600;
TinyGPSPlus gps;
SoftwareSerial sas(RXPin, TXPin);
//gps end

//rtc start
RTC_DS3231 rtc;
SoftwareSerial XBee(2, 3); // RX, TX
unsigned long start_time = 0;
unsigned long end_time = 0;
//rtc end


//Variables for data frame
String times;
String hh,mm,ss,packetcounts;
String st,sp,sa,lati,longi,alt,hrs,mins,secs;
int na,np,nt,nlati,nlongi,nalt,nhrs,nmins,nsecs,ntimes,packetcount=0,packclen;
char char_array7[10],char_array8[10],char_array9[10],char_array10[10],char_array11[10],char_array12[10],char_array13[10],char_array14[10], char_array15[25], char_array16[25], char_array17[10];
char char_array0[10],char_array1[10],char_array2[10],char_array3[10],char_array4[10],char_array5[10],char_array6[10];






void setup()
{
  //Xbee Initialzing
  XBee.begin(9600);
  Serial.begin(9600);
  Wire.begin();
  //Serial1.begin(9600);
  //Serial2.begin(9600);
  sas.begin(GPSBaud);
  
  esc.attach(9);
  esc.writeMicroseconds(1000);
  
  Wire.beginTransmission(MPU_6050_addr);
  Wire.write(GYRO_CONFIG);
  Wire.write(0x08);     // setting the full scale range to plus minus 8g
  Wire.endTransmission();
  
  
  
 
  //RTC checking
  rtc.begin();
  rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));

  

  //Buzzer pin initialization
  pinMode(buzzer, OUTPUT);
}


void loop()
{
tone(0,0,0);
    
    DateTime now = rtc.now();
    DateTime future (now);
  
    if(state < 3)
    {
      XBee.listen();
      
      if(state == 0)
      {
        XBee.println(F("Payload is waiting to start."));
      }
      else if(state == 1)
      {
        XBee.println(F("Calibration waiting"));  
      }
      else if(state == 2)
      {
        XBee.println(F("Calibrated and waiting for launch"));  
      }
      
      if(state != 2)
      {
        while (XBee.available() > 0) 
        {
          char cmd = XBee.read();
          if(cmd == 'F')
          {
            XBee.println(F("FSW started."));  
            state = 1;
          }
          else if(cmd == 'C')
          {
            XBee.println(F("Calibration is success"));
            state = 2;
          }
          else if(cmd == 'R')
          {
//            myservo.write(0);
//            delay(500);
//            myservo.write(180);
          }
 else if(cmd == 'S')
          {
            hh=times.substring(0,2);
  nhrs=hh.length();
  char char_array1[nhrs+1];
  strcpy(char_array1, hh.c_str());
  
 
  mm=times.substring(3,5);
  nmins=mm.length();
  char char_array2[nmins+1];
  strcpy(char_array2, mm.c_str());
  
 
  ss=times.substring(6,8);
  nsecs=ss.length();
  char char_array3[nsecs+1];
  strcpy(char_array3, ss.c_str());
  
          }
        }  
      }  
    }
        
    
//int val;
//val=
  

{
  
  times=rtc.begin();
  ntimes=times.length();
  char char_array0[ntimes+1];
  strcpy(char_array0, times.c_str());
  
   strcpy(char_array1, hh.c_str());
   strcpy(char_array2, mm.c_str());
   strcpy(char_array3, ss.c_str());
 

  //Packetcount
  packetcounts=String(packetcount);
  packclen=packetcounts.length();
  char char_array14[packclen+1];
  strcpy(char_array14,packetcounts.c_str());
  
 

  XBee.write(char_array17);
  gps.encode(Serial.read());
  
  
  lati = gps.location.lat();
  nlati = lati.length();
  char char_array7[nlati+1];
  strcpy(char_array7, lati.c_str());
  
     
         
  longi = gps.location.lng();
  nlongi = longi.length();
  char char_array8[nlongi+1];
  strcpy(char_array8, longi.c_str());
  

  alt = gps.altitude.meters();
  nalt = alt.length()- '216';
  char altitude[nalt+1];
  strcpy(char_array9, alt.c_str());
  
     
  
  MPU_6050_GyroX = Wire.read() << 8 | Wire.read(); 
  MPU_6050_GyroY = Wire.read() << 8 | Wire.read();
  MPU_6050_GyroZ = Wire.read() << 8 | Wire.read();

  MPU_6050_GyroX = MPU_6050_GyroX/65.5;
  MPU_6050_GyroY = MPU_6050_GyroY/65.5;
  MPU_6050_GyroZ = MPU_6050_GyroZ/65.5;
    


  MPU_6050_GyroX = MPU_6050_GyroX/65.5;
  MPU_6050_GyroY = MPU_6050_GyroY/65.5;
  MPU_6050_GyroZ = MPU_6050_GyroZ/65.5;

  //<STATE>,<RTC>,<Mission_Time>, <Packet_Count>, <Packet_Type>, <Altitude>,<Latitude>,<Longitude>,<Pitch>,<Roll>,<Yaw>
 
  /*States
        0: FSW Start
        1: Calibration
        2: Calibrated and waiting for launch
        3: Launch
        4: Deploy from the rocket
        5: Released from container
        6: Landed and waiting for pickup
      */
      if(char_array9 > 0 && !decent)
      {
        state = 3;
        climb = true;
      }
      else if(char_array9 == 2000 )
      {
        state = 4;
      }
      else if (char_array9 == 1500)
      {
        //XBee.write();
        decent = true;
        state = 5;
        climb = false;
       
      }
     
      if(char_array9 <= 2000)
      {
        while (XBee.available() > 0) 
        {
       char cmd1 = XBee.read();
       while(cmd1 != 'T')
       {
  XBee.write(state);
  XBee.write(char_array0);         //rtc
  XBee.write(":");
  XBee.write(char_array1);         //hours
  XBee.write(":");
  XBee.write(char_array2);         //minutes
  XBee.write(":");
  XBee.write(char_array3);         //seconds
  XBee.write(",");
  XBee.write(char_array14);        //PacketCount
  XBee.write(",");
  XBee.write("C");                 //PacketType
  XBee.write(",");
  XBee.write(char_array9);         //altitude     
  XBee.write(",");
  XBee.write(char_array7);         //latitude
  XBee.write(",");
  XBee.write(char_array8);         //longitude
  XBee.write(",");
  XBee.print(MPU_6050_GyroX, 2);   //PITCH
  XBee.write(F(","));
  XBee.print(MPU_6050_GyroY, 2);   //Roll
  XBee.write(F(","));
  XBee.print(MPU_6050_GyroZ, 2);   //Yaw
 }
packetcount++;
delay(1000);
        }
   if(char_array9 == 0)
   {     
  XBee.available() == 0;  
  {     
    state = 6;
        climb = false;
        decent= false;
  digitalWrite(10,HIGH);
   //void tone(2500);
  }
  }
      }
}
}
