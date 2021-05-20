#include <SoftwareSerial.h>
#include <XBee.h>
#include <Wire.h>
#include <Adafruit_BMP280.h>
#include <String.h>
#define NUM_SAMPLES 10

Adafruit_BMP280 bmp;
const int buzzer = 10;          //Buzzer
int sum = 0;                    // sum of samples taken
unsigned char sample_count = 0; // current sample number
char climb=false;
char decent=false;
//char state= ('0','1','2','3','4','5','6');
unsigned int state = 0;
int packetcount = 0;


float V_0 = 5.0; // supply voltage to the pressure sensor
float rho = 1.204; // density of air 

// parameters for averaging and offset
int offset = 0;
int offset_size = 10;
int veloc_mean_size = 20;
int zero_span = 2;


void setup()
{
  //Xbee Initialzing
  XBee.begin(9600);
  Serial.begin(9600);
  pinMode(buzzer, OUTPUT);

  if (!bmp.begin()) 
  {
    while (1);
  }

  /* Default settings from datasheet. */
  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,     /* Operating Mode. */
                  Adafruit_BMP280::SAMPLING_X2,     /* Temp. oversampling */
                  Adafruit_BMP280::SAMPLING_X16,    /* Pressure oversampling */
                  Adafruit_BMP280::FILTER_X16,      /* Filtering. */
                  Adafruit_BMP280::STANDBY_MS_500); /* Standby time.
                  */
alti=bmp.readAltitude(1005.8)

for (int ii=0;ii<offset_size;ii++){
    offset += analogRead(A0)-(1023/2);
  }
  offset /= offset_size;

}
void loop()
{
float adc_avg = 0; float veloc = 0.0;
  
// average a few ADC readings for stability
  for (int ii=0;ii<veloc_mean_size;ii++){
    adc_avg+= analogRead(A0)-offset;
  }
  adc_avg/=veloc_mean_size;
  
  // make sure if the ADC reads below 512, then we equate it to a negative velocity
  if (adc_avg>512-zero_span and adc_avg<512+zero_span){
  } else{
    if (adc_avg<512){
      veloc = -sqrt((-10000.0*((adc_avg/1023.0)-0.5))/rho);
    } else{
      veloc = sqrt((10000.0*((adc_avg/1023.0)-0.5))/rho);
    }
  }
  //<STATE>,<Packet_Count>, <Packet_Type>, <Altitude>,<Temperature>,<Airspeed>
 
  /*States
        0: FSW Start
        1: Calibration
        2: Calibrated and waiting for launch
        3: Launch
        4: Deploy from the rocket
        5: Released from container
        6: Landed and waiting for pickup
      */
      
      if(alti > 0 && !decent)
      {
        XBee.listen();
        while (XBee.available() > 0) 
      
      {
        state = 3;
        climb = true;
      }
      else if(alti == 2000 )
      {
        state = 4;
      }
      else if (alti == 1500 && (decent ))
      {
        decent = true;
        state = 5;
        climb = false;
       
      }
      else if( alti < 20 && decent){
        state = 6;
        climb = false;
        decent= false;
      }

  if(state >=5 )
  {
    while(alti = 0)
          {
  XBee.write(state);
  XBee.write(",");
  XBee.write(packetcount);        //PacketCount
  XBee.write(",");
  XBee.write("SP");                //PacketType
  XBee.write(",");
  XBee.write(alti);  //altitude     
  XBee.write(",");
  XBee.print(bmp.readTemperature());              //temperature     
  XBee.write(F(","));
  XBee.print(veloc);            //airspeed    
 }
packetcount++;
delay(1000); 
  }
 //Buzzer
      digitalWrite(10,HIGH);
 
      }
}
