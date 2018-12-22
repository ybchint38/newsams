/*
SQLyog Community v8.71 
MySQL - 5.6.16-log : Database - SAMS
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`SAMS` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `SAMS`;

/*Table structure for table `at_login` */

DROP TABLE IF EXISTS `at_login`;

CREATE TABLE `at_login` (
  `ID` varchar(45) NOT NULL,
  `PWD` varchar(45) NOT NULL,
  `FID` varchar(4) DEFAULT NULL,
  `STATUS` varchar(6) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `at_login` */

insert  into `at_login`(`ID`,`PWD`,`FID`,`STATUS`) values ('admin','admin','f100','LOGOUT');

/*Table structure for table `attendancedetail` */

DROP TABLE IF EXISTS `attendancedetail`;

CREATE TABLE `attendancedetail` (
  `ID` int(10) unsigned NOT NULL,
  `SID` varchar(13) NOT NULL,
  `STATUS` int(10) NOT NULL,
  `SRNO` int(10) unsigned DEFAULT NULL,
  KEY `FK_attendancemaster` (`ID`),
  KEY `FK_attendancedetail_sid` (`SID`),
  CONSTRAINT `FK_attendancedetail_sid` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_attendancemaster` FOREIGN KEY (`ID`) REFERENCES `attendancemaster` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `attendancemaster` */

DROP TABLE IF EXISTS `attendancemaster`;

CREATE TABLE `attendancemaster` (
  `ID` int(10) unsigned NOT NULL,
  `ADATE` datetime NOT NULL,
  `PERIODNO` int(10) unsigned NOT NULL,
  `SCODE` varchar(8) NOT NULL,
  `CLASSID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_classid` (`CLASSID`),
  CONSTRAINT `FK_classid` FOREIGN KEY (`CLASSID`) REFERENCES `class` (`CLASSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `class` */

DROP TABLE IF EXISTS `class`;

CREATE TABLE `class` (
  `CLASSID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `COURSE` varchar(10) NOT NULL DEFAULT 'MCA',
  `SEMESTER` int(10) unsigned NOT NULL,
  `SECTION` varchar(1) NOT NULL,
  `BATCHNO` int(10) unsigned NOT NULL,
  PRIMARY KEY (`CLASSID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `classroom` */

DROP TABLE IF EXISTS `classroom`;

CREATE TABLE `classroom` (
  `ROOMNO` int(10) unsigned NOT NULL,
  `COURSE` varchar(10) DEFAULT NULL,
  `SEMESTER` int(10) unsigned DEFAULT NULL,
  `SECTION` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ROOMNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `classroom` */

insert  into `classroom`(`ROOMNO`,`COURSE`,`SEMESTER`,`SECTION`) values (1,NULL,NULL,NULL),(2,NULL,NULL,NULL),(3,NULL,NULL,NULL),(4,NULL,NULL,NULL),(5,NULL,NULL,NULL),(6,NULL,NULL,NULL),(7,NULL,NULL,NULL),(8,NULL,NULL,NULL),(9,NULL,NULL,NULL),(10,'MCA',4,'B'),(11,'MCA',4,'A'),(12,NULL,NULL,NULL),(13,NULL,NULL,NULL),(14,NULL,NULL,NULL),(15,NULL,NULL,NULL);

/*Table structure for table `computerlab` */

DROP TABLE IF EXISTS `computerlab`;

CREATE TABLE `computerlab` (
  `LABID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `LABNAME` varchar(45) NOT NULL,
  PRIMARY KEY (`LABID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `computerlab` */

insert  into `computerlab`(`LABID`,`LABNAME`) values (1,'Computer Lab 1'),(2,'Computer Lab 2'),(3,'Computer Lab 3'),(4,'Internet Lab');

/*Table structure for table `constraint_common` */

DROP TABLE IF EXISTS `constraint_common`;

CREATE TABLE `constraint_common` (
  `CID` int(10) unsigned NOT NULL,
  `VALUE` int(10) unsigned NOT NULL,
  PRIMARY KEY (`CID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `constraint_common` */

insert  into `constraint_common`(`CID`,`VALUE`) values (1,14),(2,16),(3,18),(4,4),(5,2);

/*Table structure for table `constraints` */

DROP TABLE IF EXISTS `constraints`;

CREATE TABLE `constraints` (
  `SEMESTER` int(10) unsigned NOT NULL,
  `CID` int(10) unsigned NOT NULL,
  `VALUE` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `constraints` */

insert  into `constraints`(`SEMESTER`,`CID`,`VALUE`) values (1,5,4),(1,1,4),(1,2,8),(1,3,2),(1,4,2),(6,1,0),(6,2,0),(6,3,0),(6,4,0),(6,5,0),(5,1,0),(5,2,0),(5,3,0),(5,4,0),(5,5,0),(4,1,5),(4,2,8),(4,3,2),(4,4,0),(4,5,4),(3,1,0),(3,2,0),(3,3,0),(3,4,0),(3,5,0),(2,1,0),(2,2,0),(2,3,0),(2,4,0),(2,5,0);

/*Table structure for table `faculty` */

DROP TABLE IF EXISTS `faculty`;

CREATE TABLE `faculty` (
  `FID` varchar(4) NOT NULL,
  `FNAME` varchar(30) NOT NULL,
  `CONTACTNO` varchar(13) DEFAULT NULL,
  `DEPARTMENT` varchar(60) DEFAULT NULL,
  `IMAGE` mediumblob,
  `TIMESLOT` int(11) DEFAULT '1',
  `DESIGNATION` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`FID`),
  UNIQUE KEY `FNAME` (`FNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `faculty` */

insert  into `faculty`(`FID`,`FNAME`,`CONTACTNO`,`DEPARTMENT`,`IMAGE`,`TIMESLOT`,`DESIGNATION`) values ('f100','Admin',NULL,NULL,NULL,NULL,'Head of Department');

/*Table structure for table `facultyrole` */

DROP TABLE IF EXISTS `facultyrole`;

CREATE TABLE `facultyrole` (
  `FID` varchar(4) NOT NULL,
  `ROLE` int(10) unsigned NOT NULL,
  `COURSE` varchar(10) DEFAULT NULL,
  `SEMESTER` int(10) unsigned DEFAULT NULL,
  `SECTION` varchar(1) DEFAULT NULL,
  KEY `FK_fid` (`FID`),
  CONSTRAINT `FK_fid` FOREIGN KEY (`FID`) REFERENCES `faculty` (`FID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `facultyrole` */

insert  into `facultyrole`(`FID`,`ROLE`,`COURSE`,`SEMESTER`,`SECTION`) values ('f100',2,NULL,NULL,NULL);

/*Table structure for table `facultyschedule` */

DROP TABLE IF EXISTS `facultyschedule`;

CREATE TABLE `facultyschedule` (
  `FID` varchar(4) NOT NULL,
  `PERIODNO` int(10) unsigned NOT NULL,
  `MON` varchar(25) DEFAULT NULL,
  `TUE` varchar(25) DEFAULT NULL,
  `WED` varchar(25) DEFAULT NULL,
  `THUS` varchar(25) DEFAULT NULL,
  `FRI` varchar(25) DEFAULT NULL,
  `SAT` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `facultysubject` */

DROP TABLE IF EXISTS `facultysubject`;

CREATE TABLE `facultysubject` (
  `FID` varchar(4) NOT NULL,
  `SCODE` varchar(8) NOT NULL,
  KEY `FK_facultysubject_fid` (`FID`),
  KEY `FK_facultysubject_scode` (`SCODE`),
  CONSTRAINT `FK_facultysubject_fid` FOREIGN KEY (`FID`) REFERENCES `faculty` (`FID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_facultysubject_scode` FOREIGN KEY (`SCODE`) REFERENCES `subject` (`SCODE`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `laballotment` */

DROP TABLE IF EXISTS `laballotment`;

CREATE TABLE `laballotment` (
  `LABID` int(10) unsigned NOT NULL,
  `COURSE` varchar(10) DEFAULT NULL,
  `SEMESTER` int(10) unsigned DEFAULT NULL,
  `SECTION` varchar(1) DEFAULT NULL,
  `SCODE` varchar(8) DEFAULT NULL,
  KEY `FK_laballotment_labid` (`LABID`),
  CONSTRAINT `FK_laballotment_labid` FOREIGN KEY (`LABID`) REFERENCES `computerlab` (`LABID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `leavedetail` */

DROP TABLE IF EXISTS `leavedetail`;

CREATE TABLE `leavedetail` (
  `LID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SID` varchar(12) NOT NULL,
  `CLASSID` int(10) unsigned NOT NULL,
  `LFROM` datetime NOT NULL,
  `LTO` datetime NOT NULL,
  `APPROVALDATE` datetime DEFAULT NULL,
  `DETAIL` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`LID`),
  KEY `FK_leavedetail_sid` (`SID`),
  KEY `FK_leavedetail_classid` (`CLASSID`),
  CONSTRAINT `FK_leavedetail_classid` FOREIGN KEY (`CLASSID`) REFERENCES `class` (`CLASSID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_leavedetail_sid` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `COURSE` varchar(10) NOT NULL,
  `SEMESTER` int(10) unsigned NOT NULL,
  `SECTION` varchar(1) NOT NULL,
  `PERIODNO` varchar(8) NOT NULL,
  `MON` varchar(8) DEFAULT NULL,
  `TUE` varchar(8) DEFAULT NULL,
  `WED` varchar(8) DEFAULT NULL,
  `THUS` varchar(8) DEFAULT NULL,
  `FRI` varchar(8) DEFAULT NULL,
  `SAT` varchar(8) DEFAULT NULL,
  KEY `FK_schedule_scode` (`MON`),
  KEY `FK_schedule_2` (`TUE`),
  KEY `FK_schedule_3` (`WED`),
  KEY `FK_schedule_4` (`THUS`),
  KEY `FK_schedule_5` (`FRI`),
  KEY `FK_schedule_6` (`SAT`),
  CONSTRAINT `FK_schedule_2` FOREIGN KEY (`TUE`) REFERENCES `subject` (`SCODE`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_schedule_3` FOREIGN KEY (`WED`) REFERENCES `subject` (`SCODE`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_schedule_4` FOREIGN KEY (`THUS`) REFERENCES `subject` (`SCODE`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_schedule_5` FOREIGN KEY (`FRI`) REFERENCES `subject` (`SCODE`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_schedule_6` FOREIGN KEY (`SAT`) REFERENCES `subject` (`SCODE`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_schedule_scode` FOREIGN KEY (`MON`) REFERENCES `subject` (`SCODE`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `SID` varchar(12) NOT NULL,
  `SNAME` varchar(30) NOT NULL,
  `FATHERNAME` varchar(30) DEFAULT NULL,
  `EMAILID` varchar(50) DEFAULT NULL,
  `CONTACTNO` varchar(13) NOT NULL,
  `CLASSID` int(10) unsigned NOT NULL,
  `SRNO` int(10) unsigned DEFAULT NULL,
  `ADDRESS` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `FK_student_1` (`CLASSID`),
  CONSTRAINT `FK_student_1` FOREIGN KEY (`CLASSID`) REFERENCES `class` (`CLASSID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `studentclass` */

DROP TABLE IF EXISTS `studentclass`;

CREATE TABLE `studentclass` (
  `SID` varchar(13) NOT NULL,
  `CLASSID` int(10) unsigned NOT NULL,
  `SRNO` int(10) unsigned NOT NULL,
  KEY `FK_studentclass_1` (`CLASSID`),
  CONSTRAINT `FK_studentclass_1` FOREIGN KEY (`CLASSID`) REFERENCES `class` (`CLASSID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `suballotment` */

DROP TABLE IF EXISTS `suballotment`;

CREATE TABLE `suballotment` (
  `FID` varchar(4) NOT NULL,
  `SCODE` varchar(8) NOT NULL,
  `COURSE` varchar(10) DEFAULT NULL,
  `SEMESTER` int(10) unsigned DEFAULT NULL,
  `SECTION` varchar(1) DEFAULT NULL,
  KEY `FK_suballotment_fid` (`FID`),
  KEY `FK_suballotment_scode` (`SCODE`),
  CONSTRAINT `FK_suballotment_fid` FOREIGN KEY (`FID`) REFERENCES `faculty` (`FID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_suballotment_scode` FOREIGN KEY (`SCODE`) REFERENCES `subject` (`SCODE`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `SCODE` varchar(8) NOT NULL,
  `SNAME` varchar(60) NOT NULL,
  `SYLLABUS` mediumblob,
  `FILENAME` varchar(20) DEFAULT NULL,
  `TYPE` int(10) unsigned NOT NULL,
  `COURSE` varchar(10) NOT NULL,
  `SEMESTER` int(10) unsigned NOT NULL,
  PRIMARY KEY (`SCODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `subject` */

insert  into `subject`(`SCODE`,`SNAME`,`SYLLABUS`,`FILENAME`,`TYPE`,`COURSE`,`SEMESTER`) values ('MCA101','Information Technology',NULL,'NA',0,'MCA',1),('MCA102','Mathematical Foundation of Computer Science',NULL,'NA',0,'MCA',1),('MCA103','C Programing Language',NULL,'NA',0,'MCA',1),('MCA104','Computer Architecture and Assembly Level Programing',NULL,'NA',0,'MCA',1),('MCA105','Communication Skills',NULL,'NA',0,'MCA',1),('MCA106','C Programing Lab',NULL,'NA',0,'MCA',1),('MCA107','8086 Programing lab',NULL,'NA',2,'MCA',1),('MCA401','Artificial Intelligence and Applications',NULL,NULL,0,'MCA',4),('MCA402','Mobile Communications',NULL,NULL,0,'MCA',4),('MCA403','Computer Graphics and Multimedia','PK\0\0\0\0\0!\0İü•7f\0\0 \0\0\0[Content_Types].xml ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0´TËnÂ0¼Wê?D¾V‰¡‡ªªú8¶H¥`ì\rXõKöòúûnDUA*å)YïÌììÄƒÑÚšl	1iïJÖ/z,\'½ÒnV²ÉK~Ï²„Â)a¼ƒ’m ±Ñğúj0ÙHu»T²9bxà<É9X‘\nÀQ¥òÑ\n¤×8ãAÈO1~Ûëİqé‚Ãk6<A%³ç5}Ş*‰`Ë·k®’‰Œ–I)_:õƒ%ß1ÔÙœIsÒ\rÉ`ü C]9N°ë{#k¢VEÄWaI_ù¨¸òrai†¢æ€N_UZBÛ_£…è%¤D[S´+´Ûë?ª#áÆ@ú[Ü.zÒ9>$N{9›êÍ+P9Y ¢†vuÇGD²ìÃï»ÆoR€”wàÍ³¶\rÌIÊŠ~‰‰˜\Z8›ïWòZè“\"V0}¿˜ûßÀ»„´ù“>şÁŒıuQwHoî·á\0\0\0ÿÿ\0PK\0\0\0\0\0!\0‘\Z·ó\0\0\0N\0\0\0_rels/.rels ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Œ’ÛJA†ïßaÈ}7Û\n\"ÒÙŞH¡w\"ë„™ìwÌ¤Ú¾½£ ºPÛ^æôçËOÖ›ƒ›Ô;§<¯aYÕ Ø›`Gßkxm·‹PYÈ[š‚g\rGÎ°inoÖ/<‘”¡<Œ1«¢â³†A$>\"f3°£\\…È¾TºI	S‘ÌõŒ«º¾ÇôWš™¦ÚY\rigï@µÇX6_Ö]7\Z~\nfïØË‰ÈaoÙ.b*lIÆrj)õ,\Zl0Ï%‘b¬\n6ài¢ÕõDÿ_‹…,	¡	‰Ïó|uœZ^tÙ¢yÇ¯;!Y,}{ûCƒ³/h>\0\0ÿÿ\0PK\0\0\0\0\0!\0Öd³Qú\0\0\01\0\0\0word/_rels/document.xml.rels ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¬’ÍjÃ0„ï…¾ƒØ{-;ı¡„È¹”@®­û\0Š½ş¡²$´›¶~û\nCR‡÷â‹`Fhæ“´›íwoÄ\'êœU%)´¥«:Û(x/vwÏ ˆµ­´qH°Íoo6¯h4ÇCÔvDL±¤ eök)©l±×”86îÔ.ôš£ôºüĞ\rÊUš>É0Í€ü\"Sì+a_İƒ(›ÿÏvuİ•øâÊc–¯TÈ/<¼!s¼ÅX\Zd3‰´ ¯ƒ¬–¡?\'g![?óü4ê¹úÇ%ë9ş¶Rk6Çğ°$Cí,ú`&gë!/=ÿ\0\0ÿÿ\0PK\0\0\0\0\0!\0®€Å¢\0\0Á–\0\0\0\0\0word/document.xmlì]ÛrÛ6\Z¾ß™}Œ.v¶3²,ËIÓÈµ:e\'ÚT©Öv“é%LB\"j’à¤Tå*ïĞ«Îì¾\\d?%ÚttÇ&ÜX2(@üÇï?àÇŸşğ\\2eRqá7Zía¾%lîO¿^ïıĞ *¤¾M]á³ãÆœ©ÆO½¿ÿíÇY×Vä1?$¸„¯ºSuÂ0èîï+ËaU-0ÇBz4Ä¿r²ïQy{–ğ\Zòkîòp¾ßi·¿o¤—ÇHúİô{·¤PbêSºb<æK?gÈMî›œÙO9¾ã¾d.AøÊáZ\\ÍÛõj˜¢³¸ÈôK“˜zîâw³`“»Ù’Î°›<öLH;ÂbJa´ŸÌ®xĞşÒ½Ó¨/‘±É#äï¹xr?»Œ¦[ëŸ-^‹·ŸÜ{__j9¼‹héZØsıY´h_7ÚíóÎ‹³óNc1Ôgc\Z¹áİ#£•¡ø\"#©?hŠË€Z¬†KL©‹Ÿ5öóGŞİ>bÿ©ğ‚OœpàÛ·*\\o£t2˜eÖu¹yçYöÏEäb@ß=¹›LG?Túdeq~Ü¸âSä›‘áQ¼ÇY×9ñUñKİ=!Êuò÷TÅŸê#®Ï´s˜Ü\\}<Õ÷\\ÛÇÖO„Ï }2ıNJú”³nØì=k’S/¼–4p¸¥È?¨‘!¨/ÓæTO*Œ§–N0&ÌßĞÔ]jÛš¦^Ş¥©d¬L4µş)5üúnpµ70ôò2h)uÖ¿ûÇ”:Û<hB ]-ã!½É“SÖèeB\'G+Rˆñ™”.á<À)I½ËÊ0•ú_]¨n9½^&5»›LåÌ·K:‘âu\"6sŸk{®I,—Â8‚­Ûw©š8	7QMÒgSæŠ 6`Å˜¼¡ÒQÉÒß^ÂâŒÿ…íš{[š~RÙÁPÙ†Z—B¢z\\–É‹»µrôsà¨·õ¹\n\\:×Má‘Ÿ4KÙP‹|kÔ\nù”á;L·å¯íôôÔ²™{K†Ëvtvã²Ø]úªÀ6Ïu¯*şIá#2¥’‹HêN„ä¡ãá+\\¶Ğa\\‚/H®beÀ¥å22a>¨Që‡=’#¶–TsİR+óWÚ@ñê}şô—Úh:•1ÍªÔ‹äq›‚ûáFS©ÜY1cåæf÷£\nîê™G©øÎM\0ÖÎTåø!ÓDÍ\Z(˜šË.èw€ş™9±´\"òæº‘g;Ê³ë{pér!D»?åC4 â]hùÑğÒy.½“0”ü\Za\nE\0+‰(Ô~o ¹hjªe/BHuî2àTÂ2v[`$2Ây“œHFÉ˜C@!Ö”éœyiQ?9=5nó_õ¬¯k!’/çµ4¿4Ë±+„sÏ’uê`ø!§.§:ä¿nõbô¢rÖsÈ,Ççÿ‰˜j‘«™ 68QøÔ%¡¤¾JÒHt¾ÆQ2dohD`œŞ°Ş´ÔF›²ÀFëy¨Üh¯I¤c0ßØØe@Ù…O”Ã˜Äì´eáö»Å;Í[ÿë\"ûÄ†ya9T£õÌ¼CmÁPK\\}^*9OÏÿAè~wŸzöıúnİîO¹âù×ÿA(f›ààcÒÈ6Ï¥©â=g:O*)®Ü§À\0šä÷m1k’)\"$CÂƒ ÖÒÉAŠåá[ú9Å§äD¬\'w\'·YØ%ÁUÏİÏ`¢ÙÔ$Ür„|`‡ùä2BÈ_º°a›à/w>¼`—£•£5ˆÉ8ÂÀX_·šÕôÿ3bm’Ã~¶‚¤KŞéò—dvnâFúé·RÜxÿ Š4)ìi›£˜š>2.\n¯`[d6EÆ.Kã‚pŸ8ÂÚ=ÓiR+F‰qtŒÙºä“âtê\ZÓ6¬†•<a3·K~ÖeQD‰HÆŸ6#„ĞWğ§uìVş¼:<¡¹4jêØåRe°!³RÄº6!0Ò$qun¼2ÀúŠ‰ê9*’cÔÎ¬å‹…!puª+¢jàCXı¨¦tKW‰L¤s/MÇBt–E¬Ä„İ\r#¯_5Éoƒ7Ééğ7Ô.\\¾\',´Œ]ô owhu‰µT\0~ŸvÆ!}T‡ôë“È60¡Æ÷ú$Å€»äÊ‘Œí­&1XÂ·XÂo¾±(€Zf=ÄÃ’Å•§~ÛÍğ¬¤ÎİŠP‘j@RÛ¬íR,UÖ*[[\"TE³¬…\"^Å\'¾N´ŒycÑ\"5EU-\n½ØGÎd}«¼†,¬İ$¯örÊô>—»Ôõ^(‰ErïFó¨\\z¯S¢¼òéxIÄ2©¼ÏÍİDjUP9Õ$ï¾d;²HÒ#™×!¡òšƒwäœĞ?8X	+ö{’¹§ºd„ì;×enÚb„lÀ\"uÑ€âŒÉ·EBŞnÛˆ‹§FP\\]×œHæ	\\æˆ\\Së†h¬Ğ}<ÔDc† tÈu4£½–.£GšlZk’\nş<8R W,H#øwüßtb“\'iåCNĞ)F\n;J’Ø‘\rçeıš+ßu®z€.¤h£˜FŞzÜ»	r§—ƒ&ôÏšdxŠo¦3àÃv›üVP›†ù^’_Öw¦—­ÛÛ=,­ÚQ\'ÇHÚ“·I.®Î›äjp¿ÃA<ó¯ÑÙk´G B2Œ¿.™‰ªqaØ­ºùL‚QªtĞx~O×J±zß	’6Ñµ	ÉÒE$elÍFÜ>á«¶äúlÍW¿üòöÒH¦§“LÑ‹­\Zdvô[o•~Üì–jævhÃeØ\ZµÈ+zˆâó§ÿŞîşùÓÿÈ?;øİFo`%P=õy+\n¼v•gXŞDï»&½äÍ\0*í*íæ´UDrwZä²¥›Ïjc2D®òçOJ²G(I¡aJ$6£³N\0® –cÄÆJó¼r¨˜Ø(öy¿ÛZ”ô{Cë5¶yqĞàf#İVbÉndº~×Õ¶âmq†Ô÷Ó`èFT¿bÑ•ƒ/!K§UçŞb»§õíú×â›´½Ó¥$‹Îï\Z´wíæwí1\n×(Ü²·W7\n×ì³¶Ø˜íÖÎjëÛŒ-ó…Ë§‡µ°İÜ\n¼¦>¢A},¼±› !ÿˆœ¹Lo‰£t€èî†kİˆ\nÅÆ(ykÔXsÆš[Zs½çq-ikˆm1±hˆ—uiGçb=ê‘µÆ¾o‘sá²ym…÷@ºëÚâ¥µ&—yI™Ó(öˆúí/à³ú>à‰ª/ôSØh&åÅpŠ½À\Z l˜r×¶ÆÎ#¤å3ğŒIfL²¥IVÌ«/êî¾¢UçÕâÅ© }O£	¶¦û6õœ0–\ZŠ†-u_btA\"Nål‘!½Ñ!^’BŞhòù-b|TÅİIÒùÔj»ºxHÑ¢]]±0@QÎS>øÚ\rßuÛƒ3»E:íöÒˆ‘6mà!\'Ÿ*‹óãÆ•±EÖQøu°Eà²^;´¦Û|¾­Ã\nø¶ËĞçî–4½“áRr÷®XKÔüzË±ÑO=—h£Y•Ún¼rèMTÓÆ˜°‰WÊœ.ç*d^Ú]&y!5·€&Õ$£vU€\ZúãF\\\r‡Î\\ŞKÄT%Ãf §ó8¾¥R7Ş	rI^1Aå¨©ÀíŠõP©ÅÁÅ\n›±m4“òz=˜>¡“rÇş*=zrĞFv\"Ÿ OË©CìÆ€ŠÙÖâ»fü>Ÿp0ı\nÄ¢ñ‡(º“osš\'¸Ş_\\\nõÌË~çùá©ÀRqû¢ÏÆõòÇvû¼óâì¼³82ZÒõÉÕ{Ùï6°ë®“0+ï¯¤\"•Ø#\Z ‡ëòir\rYH\"€ÕŒ£¥úŒø‰“(õî—–#¸Åòd“Æÿ\0\0ÿÿL[‚0E·bº°€ühâ—	Ñ@[ 	0¤Òèê‹şÎ;w.µJ`iv.5VËGÎÂğ|åItaEæÒ©}¾Ér9Ûs‡Œ¸#NNÄÁÒp¯¾a„‰êñÒbtÛá*k@„aÕ½j6n§*©LÎÜo\0p#Û½ü­Ğ[Úf§J¨%â¯ nFKrz=ªR£ +£ƒÿ‹Œ°ùò@‘yP#\0\0\0ÿÿ\0PK\0\0\0\0\0!\0–µ­â–\0\0P\0\0\0\0\0word/theme/theme1.xmlìYOoÛ6¿Øw toc\'v\ZuŠØ±›-MÄn‡i‰–ØP¢@ÒI}Úã€Ãºa‡Øm‡a[Ø¥û4Ù:lĞ¯°GR’ÅX^’6ØŠ­>$ùãûÿ©«×îÇ!)OÚ^ırÍC$ñy@“°íİö/­yH*œ˜ñ„´½)‘Şµ÷ß»Š×UDb‚`}\"×qÛ‹”J×—–¤ÃX^æ)I`nÌEŒ¼Šp)øèÆli¹V[]Š1M<”àÈŞ\Z©OĞP“ô6râ=¯‰’zÀgb Ig…ÁuSÙebÖö€OÀ†ä¾òÃRÁDÛ«™Ÿ·´qu	¯g‹˜Z°¶´®o~ÙºlAp°lxŠpT0­÷­+[}`j×ëõº½zAÏ\0°ïƒ¦V–2ÍF­ŞÉi–@öqv·Ö¬5\\|‰şÊœÌ­N§Óle²X¢dsøµÚjcsÙÁÅ7çğÎf·»êà\rÈâWçğı+­Õ†‹7 ˆÑä`­ÚïgÔÈ˜³íJø\ZÀ×j|†‚h(¢K³óD-Šµßã¢\0\rdXÑ©iJÆØ‡(îâx$(Öğ:Á¥;äË¹!ÍI_ĞTµ½S1£÷êù÷¯?EÇ?øéøáÃã?ZBÎªmœ„åU/¿ıìÏÇ£?~óòÑÕxYÆÿúÃ\'¿üüy5Òg&Î‹/ŸüöìÉ‹¯>ıı»GğMGeøÆD¢›äíó3Vq%\'#q¾ÃÓòŠÍ$”8ÁšKıŠôÍ)f™w9:Äµàå£\nx}rÏx‰‰¢œw¢ØîrÎ:\\TZaGó*™y8IÂjæbRÆíc|XÅ»‹Ç¿½I\nu3KGñnD1÷NIBÒsü€\níîRêØu—ú‚K>Vè.EL+M2¤#\'šf‹¶i~™Véşvl³{u8«Òz‹ºHÈ\nÌ*„æ˜ñ:(W‘â˜•\r~«¨JÈÁTøe\\O*ğtHG½€HYµæ–\0}KNßÁP±*İ¾Ë¦±‹ŠTÑ¼9/#·øA7ÂqZ…Ğ$*c?¢íqUßån†èwğNºû%»O¯·ièˆ4=3Ú—Pª\nÓäïÊ1£Pm\\\\9†øâëÇ‘õ¶âMØ“ª2aûDù]„;Yt»\\ôí¯¹[x’ìóùç]É}Wr½ÿ|É]”Ïg-´³Ú\neW÷\r¶)6-r¼°CSÆjÊÈ\rišd	ûDĞ‡A½ÎœIqbJ#xÌêºƒ6kàê#ª¢A„Sh°ë&ÊŒt(QÊ%ìÌp%m‡&]ÙcaSl=XíòÀ¯èáü\\P1»MhŸ9£Mà¬ÌV®dDAí×aV×B™[İˆfJÃ­P|8¯\ZÖ„AÛV^…ó¹f\rÌH ín÷ŞÜ-Æé\"á€d>ÒzÏû¨nœ”ÇŠ¹	€Ø©ğ‘>äbµ·–&ûÜÎâ¤2»Æv¹÷ŞÄKyÏ¼¤óöD:²¤œœ,AGm¯Õ\\nzÈÇiÛÃ™ã¼.uÏ‡YC¾6ìOMf“å3o¶rÅÜ$¨Ã5…µûœÂNH…T[XF64ÌT,Ñœ¬üËM0ëE)`#ı5¤XYƒ`ø×¤\0;º®%ã1ñUÙÙ¥m;ûš•R>QD¢àØDìcp¿UĞ\' ®&LEĞ/p¦­m¦Üâœ%]ùöÊàì8fi„³r«S4Ïd7y\\È`ŞJân•²åÎ¯ŠIùR¥Æÿ3Uô~7+ö€×¸#¯mq¨BiDı¾€ÆÁÔˆ¸‹…i*¸L6ÿ9ÔÿmÎY\Z&­áÀ§öiˆ…ıHE‚=(K&úN!VÏö.K’e„LD•Ä•©{D	ê\Z¸ª÷vEê¦šdeÀàNÆŸûeĞ(ÔMN9ßœ\ZRì½6şéÎÇ&3(åÖaÓĞäö/D¬ØUíz³<ß{ËŠè‰Y›ÕÈ³˜•¶‚V–ö¯)Â9·Z[±æ4^næÂç5†Á¢!Já¾é?°ÿQá3ûeBo¨C¾µÁ‡MÂ¢ú’m<.vp“´Á¤IYÓf­“¶Z¾Y_p§[ğ=al-ÙYü}NcÍ™ËÎÉÅ‹4vfaÇÖvl¡©Á³\'S†ÆùAÆ8Æ|Ò*uâ£{àè-¸ßŸ0%M0Á7%¡õ˜<€ä·ÍÒ¿\0\0\0ÿÿ\0PK\0\0\0\0\0!\0æ©ZÖ\0\0J\0\0\0\0\0word/settings.xmlœUÛn›@}¯Ô@<×;iQp¤:q/rÚª$0À«ìM»‹‰ûõ6$mU}òî93gçŠ/.8D*E.Oâ0 ¢”MŞİîïÃÀX0)H‰	/7oß\\ô©!Ö¢™	PB˜Tfa§EjÊ–p0NK-¬í¢”<•uMK2ı„“‡ÎÂÖZ•FÑät\"¨VKÍÁš©›hô¼’eÇ‰°QÇg‘&,lZªŒWãÿ«†Oµ^äğZÎ¼]¿Œ_³œÒí¥®=ş%<ç ´,‰1XYÎÆt9Páeû±{ZhĞÇ\'\"lÛ/)yĞ§ŠèŠ=ã0r>,ëÜ‚%HE† dğù>m4pØ´|*RCÇì-¹•\n€\'“dÙ‚†Ò+(Qm+…Õ’y»J~“v+¹Ò˜ğ‹ëÂéÙ]ïá(;‹OE}:S8­•q6îğSJëãx—œ_ï’QË±3óá*YŸn_bf|eÒæ©‹zs1vyÀÇô¶ÀM!¸qƒƒ±ñ´Ğ÷©ğ|Ap€ÉS&ï\nO.#a80¶Ãêxgfd*jÔ©avº™•‡²òT¿ˆb/¾>ª¹ŞıIËNª½õETû—«Õ¤G…İSîqÓ¹÷8?O¨NTßÚ	FsúÔâÊW¡=ˆÆWœˆÅ]îL±sLçî³@n@)4)še2Ú´véfËâ­}?\\Š&™¸dàğæ¸á¥Ë­§ƒ3h5fìÔc§3¶òØjÆÖ[ÏØ™ÇÎÖqap!îqıüÑáµdLö¤úìÁ,ü\Z‹`ZPûêö‡W¦0-	)yÀm$µøÅU´âğ…I¼z4Y³aÙ:%g¬¡Ap·‡V=sVéXú´\"%ÅÌ¼˜×ódœQcs¢p“­Ô˜ò°âïåùO`ó\0\0ÿÿ\0PK\0\0\0\0\0!\0ocP›‹\0\0\0\0\0\0\0word/fontTable.xml¤’ßn‚0Æï—ìHï\'ÿLhœÓË],îX¤	mIO•ùö;Päbf‰d4á;íÇéï|‹Õ·*‚‹°(NX4à,:5G©O	ûÚï^¦,@ú…Ñ\"aWlµ|~ZTóÌh‡×8·	Ë+çaˆi.àÀ”BS-3V£O{\nM–ÉT¼›ô¬„vaÌù$´¢\0GÿÆ\\–ÈZ·ê·ÊØciM*©YUx?R³eÛ]PÍ5(êz…<XÙJĞEDµ	ã1ßñ1­õ;âÃzeaíæ`Q¸n#÷rJ×›Š•Dô…Rº4¿é°…ğ%”\'*œñÀ¶8çñnÇ¼%lDÂzÓ)15åŸY»gØ)4j¬ñi¶D³Æ‡òiO5}†~>w$öR	>D|\ZÕ=‘˜OˆÄ˜xÔd†½ˆØÆ·!ø(j<^w÷§›lHy¢öş½ˆxŸÇ‰l@Q4àlÔ<‰šH¿lô\'±®GoeƒóÑÛ‰&	”¨ÿd£\r	.\0\0\0ÿÿ\0PK\0\0\0\0\0!\0JØŠ’»\0\0\0\0\0\0\0\0word/webSettings.xmlŒÎÁjÃ0Æñ{aït_õ0JHR(£/Ğõ\\Gi±d$mŞöô5l—İzŸøñï_im>Q42\rğ²m¡A\n<Eº\rpy?=ï¡Qó4ù•	øF…Ãø´éKWğzF³ú©MUH;`1Ës\ZL^·œ‘ê6³$oõ”›ãyß8|$$s»¶}u‚«·Z KÌ\nZyD+,S¨ZCÒúë%	ÆÚÈÙbŠ?xb9\nEqcïşµw\0\0\0ÿÿ\0PK\0\0\0\0\0!\05\nVKz\0\0É\0\0\0docProps/app.xml ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0œRËNÃ0¼#ñQîÔiP+@[#T„8ğ¨Ô\0gËÙ$mÙ.¢Ïº¡!ˆ>íÎÚã™±áú³×Ùú ¬YåóY‘gh¤­•iWùKuwv‘g!\nSm\r®ò=†üšŸÀÆ[‡>*Q˜°Ê»İcAvØ‹0£±¡Ic}/\"µ¾e¶i”Ä[+w=šÈÊ¢X2üŒhj¬ÏÜH˜ŒWñ¿¤µ•I_x­ös¨°wZDäOI6PÙ(t¥zäÁcÑbàs`CoÖ×Ÿ_.\r%¬;á…Œ/Ëe	lÀsZI)Wş¨¤·Á61{>$%`Ó-@©lQî¼Šû$dÚÂƒ2IÊ°¡\"m^´^¸.ğE8v°•Bãš¼óFè€À~\0XÛŞ	³ç$ôXß{xq•½Má|ù\rNl¾©Øm$¦\\.Î§†\'#ØR.X“ƒ#á\0÷ô ^§[),Ób}Üów\"|~&Ÿ—³‚Ö!³#FÆÇ/Ã¿\0\0\0ÿÿ\0PK\0\0\0\0\0!\0µø”q\0\0ß\0\0\0docProps/core.xml ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0œ’ËNÃ0E÷HüCä}â$Š’TT6TB¢ÄÎØÓÖ4~Èvò÷8¦`ÅÎ3sçxæÚùâ[TÁŒåJ(‰b€¤Šq¹-ĞëzŞ¢À:\"©”„5`Ñ¢¼¾Ê©Î¨2ğl”ã8ØÀ“¤Í¨.ĞÎ9alé±‘WH_Ü(#ˆó¡ÙbMèl§q<ÇaÄÜC=Ñ€dtDêƒ©:\0£* ÅI”à³ÖöÏ†®r¡Ü5Úï4Œ{Éf´/êoËGa]×Q=ëÆğó\'ø}õôÒ­\ZrÙzE•9£™ã®‚2Çç£?ÙÃçP×§ÇÀ¨â”)UuèzN‰Öê=4µ2Ìú¶IäûXj¸vş{è$áÕ±nå_tÃİ5ÿw¾½ÆÀ‘·?¡L»{ÆĞoÓ™×	,ğvd½y§ÊÛìşa½De\Z\'³0IÂôfÌ³tÅñG»Î¤¿µ§Oˆa°O€Ş™é—,\0\0\0ÿÿ\0PK\0\0\0\0\0!\0M3ç\0\0á9\0\0\0\0\0word/styles.xml´›ßsÓ8Çßoæşß!mÉÑ!0¥ÀÑà\niç[i48VÎRhË_ÒÊV]»¶wkñDıCûÙÕ®¾ëéõÛÛ]ıä…2_ÆÇÏâˆç‰LE~½Œ¯.?>û+”fyÊ2™óe|ÇUüöÍŸ¼¾9Qú.ã*2ruR,ã­Öû“ÉD%[¾cê¹ÜóÜ<ÛÈbÇ´¹,®\'r³	/“Ãçz2=:šO\n1màj+ö*.­İ`¬İÈ\"İ2áJow™³·c\"ß÷R™¼çvÈ´²—ÅEQ^–WğÏG™kİœ0•qi7!îD.‹O§¹±yÂ™Ò§J°úÃå=û|k_¬?ô#¥kß‰TÄU¿Ì°Ÿ,[ÆÓiuçÌ:ñà^ÆòëêÏŸ]­êÎ,ckmì.cV<[Zcˆ´ú·ñşAüæ\n\\Ù³ÄÌá°æ&‡&%–“	›ëéb^]|?dæ;hYBÀ€ÕÍšËÆ¤›ÔšD¯\\¡˜§|óY&?xºÒæÁ2–¹yu~QY}·Œ_½²LssÅwâ“HSnë²¼w•oEÊÿİòüJñôşş·Pe¥ÅDrmÜŸ/ 2•~¸MøŞV™13›ä¯v@fÍª\Z:ˆ{oÜnşW!]¥l9³+)ÿ{Aõa4hj#ª\0vI¾ÎÆ›x1ŞÄËñ& xÇÍÅb¼F?ÇfÄÕF­*ñIÕ2qÅWŸ‡Ù«’µ#ZU48¢U4ƒ#Z528¢Uƒ#Z08¢•ğÁ­üh¥³wDÂ@¸šU4ƒÙ@-ìK¡3nÇ÷\nĞñH©+[MtÁ\nv]°ı6²½µévŸX®ksäôéb¹Ò…Ì¯gÄtg»tŸ¬Évû-SÂ|ÔLıtäÔ_²uÆ£¿‘¢^ºâkÅ&¶°‹Œ%|+³”Ñ%¿u%Œÿ*£•ûÊtndZ?‹ë­V[h¹ƒ°yÇ¤wÏ„³ÿY(˜ƒŞÅ4ïeÈ8*‡óºì6ş…§â°«¦ñ52wzNHs.öOÑ›¢öê\ZŒÂ&\0‚kôÀ>Â×\\èöm1ş»VôDûÿ]ãz¢}¨şü’•æ=+~D¨åµ ¯İ3™ÉbsÈª50(ò\nö\\äEìí£DbA^Áä3:Mó›¦NÉ¹¸×Q…œGÅ†…œ”†ì\"\"\'¨ÁšXã´–\0\"‹îwşSØ¿=Q›¨´ÿÖ\\Î³0-õ\rıí õğ7ô´Có°”óÜü¹DñG›u¬<,­¬\'×ï9×ø q\0\Z×\n	 úèşæñ=ß	,²,û.e‡VæY™=ˆÖõMÄ÷WÇêí®…vßDPÈ	j÷M…œF/ó}Á\nÖ7¬®Ñ£º¦R‚\"÷Í:È	 \"\n#ŞPñF€Âˆ74^¼‡!áÄÁ\"kƒ×Ôºx#@ğ\nåW}ª‹7DÖ§våßŒª¾Vú¹\r Ş\n9AmñFPÈÙéo^¡TBƒå¥Á\n#ŞPñF€Âˆ7F¼ 0â\0ïaH8ñF°ÈÚà5µ.ŞY<¨.Ş¼BÑ†GÅVıoo…œ ¶x#(äì4Õ¤\"Xä5X^¼,x…R%Š›TñFDF¼ 0â\0…oh¼xCÂ‰7‚EÖ¯©uñF€ÈòàAuñF€ÈÚğ¨xÃbüíâ Ôo…œ† zC°È	j°¼x#XP/£Å‚W\n¢DF¼…o(Œx#@ãÅ{N¼,²6xM­‹7D–ª‹7DÖ†GÅÖÈoo…œ ¶x#(äì4Õ‹7‚ENPƒå¥Á\n#ŞæhñF€à•\'€`QÒF¼…oh¼xCÂ‰7‚EÖ¯©uñF€ÈòàAuñF€ÈÚ`÷Ùšı¢èí©ÇE€İgPíj@§IÂË\0¿ó\r/Ìa&>¼;d$°Š@ì(lˆï¤üá6vÏ:\nëLHØÒ}»tjf‹“—ÿœEŸÜ˜Ö8(©‡;oÌé¡úq!8d?õİŞÙÙW;Ë­5s@Èí*\0ÁQ´ss ¨<ÖcÛs>æE8TUŞ†ÿ·-©ægC„mT²5¬ÄœˆêA•Şı$ØîŞwìŠGîdTn–»ãï¿¡Ü{öhöú­íNğŸa§xïEğŠËjÛAs8\\\ZòĞïª‚·õ:sÇÍÌçyj¢5çûàĞ\\bÓ[æÌšçg<Ë¾08œ¦å¾ûÕŒo´{z|İ°aj-µ–»îñlO3`J£îŒ»´At×L~Ø­yaN{õÌÿWi»œJ{Xnß«K·__Æ{¨^ì¬ßûVı¤Şü\0\0ÿÿ\0PK-\0\0\0\0\0\0!\0İü•7f\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0[Content_Types].xmlPK-\0\0\0\0\0\0!\0‘\Z·ó\0\0\0N\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ÿ\0\0_rels/.relsPK-\0\0\0\0\0\0!\0Öd³Qú\0\0\01\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ã\0\0word/_rels/document.xml.relsPK-\0\0\0\0\0\0!\0®€Å¢\0\0Á–\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿ\0\0word/document.xmlPK-\0\0\0\0\0\0!\0–µ­â–\0\0P\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0J\0\0word/theme/theme1.xmlPK-\0\0\0\0\0\0!\0æ©ZÖ\0\0J\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0word/settings.xmlPK-\0\0\0\0\0\0!\0ocP›‹\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0word/fontTable.xmlPK-\0\0\0\0\0\0!\0JØŠ’»\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ó \0\0word/webSettings.xmlPK-\0\0\0\0\0\0!\05\nVKz\0\0É\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0À!\0\0docProps/app.xmlPK-\0\0\0\0\0\0!\0µø”q\0\0ß\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0p$\0\0docProps/core.xmlPK-\0\0\0\0\0\0!\0M3ç\0\0á9\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\'\0\0word/styles.xmlPK\0\0\0\0\0\0Á\0\0K.\0\0\0\0','MCA403.docx',0,'MCA',4),('MCA404','Design and Analysis of Algorithms','PK\0\0\0\0\0!\0İü•7f\0\0 \0\0\0[Content_Types].xml ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0´TËnÂ0¼Wê?D¾V‰¡‡ªªú8¶H¥`ì\rXõKöòúûnDUA*å)YïÌììÄƒÑÚšl	1iïJÖ/z,\'½ÒnV²ÉK~Ï²„Â)a¼ƒ’m ±Ñğúj0ÙHu»T²9bxà<É9X‘\nÀQ¥òÑ\n¤×8ãAÈO1~Ûëİqé‚Ãk6<A%³ç5}Ş*‰`Ë·k®’‰Œ–I)_:õƒ%ß1ÔÙœIsÒ\rÉ`ü C]9N°ë{#k¢VEÄWaI_ù¨¸òrai†¢æ€N_UZBÛ_£…è%¤D[S´+´Ûë?ª#áÆ@ú[Ü.zÒ9>$N{9›êÍ+P9Y ¢†vuÇGD²ìÃï»ÆoR€”wàÍ³¶\rÌIÊŠ~‰‰˜\Z8›ïWòZè“\"V0}¿˜ûßÀ»„´ù“>şÁŒıuQwHoî·á\0\0\0ÿÿ\0PK\0\0\0\0\0!\0‘\Z·ó\0\0\0N\0\0\0_rels/.rels ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Œ’ÛJA†ïßaÈ}7Û\n\"ÒÙŞH¡w\"ë„™ìwÌ¤Ú¾½£ ºPÛ^æôçËOÖ›ƒ›Ô;§<¯aYÕ Ø›`Gßkxm·‹PYÈ[š‚g\rGÎ°inoÖ/<‘”¡<Œ1«¢â³†A$>\"f3°£\\…È¾TºI	S‘ÌõŒ«º¾ÇôWš™¦ÚY\rigï@µÇX6_Ö]7\Z~\nfïØË‰ÈaoÙ.b*lIÆrj)õ,\Zl0Ï%‘b¬\n6ài¢ÕõDÿ_‹…,	¡	‰Ïó|uœZ^tÙ¢yÇ¯;!Y,}{ûCƒ³/h>\0\0ÿÿ\0PK\0\0\0\0\0!\0Öd³Qú\0\0\01\0\0\0word/_rels/document.xml.rels ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¬’ÍjÃ0„ï…¾ƒØ{-;ı¡„È¹”@®­û\0Š½ş¡²$´›¶~û\nCR‡÷â‹`Fhæ“´›íwoÄ\'êœU%)´¥«:Û(x/vwÏ ˆµ­´qH°Íoo6¯h4ÇCÔvDL±¤ eök)©l±×”86îÔ.ôš£ôºüĞ\rÊUš>É0Í€ü\"Sì+a_İƒ(›ÿÏvuİ•øâÊc–¯TÈ/<¼!s¼ÅX\Zd3‰´ ¯ƒ¬–¡?\'g![?óü4ê¹úÇ%ë9ş¶Rk6Çğ°$Cí,ú`&gë!/=ÿ\0\0ÿÿ\0PK\0\0\0\0\0!\0‘_.ù\n\0\0÷i\0\0\0\0\0word/document.xmlì\\ËrÛ6İw¦ÿ€á¢“ÌP;nšÊ±2¶;Ä±œt\r‘ˆ\0”¬¬òİ¶?—/éI‘¢$K–ÆNH™ÓâÄïãœ‹¼~së34$RQÁ¬zÓB„;Â¥|p`}¼9©½²Ò˜»˜	N¬1QÖ›ö¯¿¼µ\\á„>á\ZA\\µ†pÕÓ:h5\ZÊñˆU]„ÃÅ¾>ÖğS\Z>–ŸÃ æ?Àšö(£zÜØm6_ZI3âÀ\n%o%MÔ|êH¡D_›GZ¢ß§I“\'ä}Ş?ÙIº½±!	ƒ>®<\Z¨Ikş¦­ˆŞ¤‘á2!†>›Ü7\nîó6Wâ|ŸÅİ	éR8D)8Û‰/¦-î4—½;@ÓDúÄ}ºç¤\'>¦<mÆhÇÌ÷O?^>^#~wÃ4•	cÑ]ê	wl\Zµ@İë«ÙìıŞ|ù‡59Õ!}2=åjêTÔÈ•4jÑ\r°C:o¡‰!fp›ÕÈ_¹˜½âş*}M>ãîÌEÁˆÃYÜ×ÔŒeÔbÔH¾»—ş¸œ0oß&ãîÈÁµ2+‡Òë†úD¡2B×ÂÇ0£–wÈÕâ+š ¥ÿ=VÑQ}…V\"Iw_Ä/W_Í;§Î5`„Mà$=3cRĞ^Zº}~|XÛkî¡QtÀ8#tÈ1+ªè£C6’jÏWF&I–Èiˆ_©Ô¼²­­R;Î«T|®H*µº—FG>^œİ ïßşAg•Î<„ÊÏêñ/‚ãYİKĞƒHZÆéƒ;$QD‰Õ¾’¤&É—*ª‰j¡œ«ytgºÎXß)EkÀN†%A¿a?ØŸ@†è¿•¨0‰ı®ÆR\'­h¢¶;T9’h2óM\nò–»c±Şe_KÙÈ.aQô3H:Ôµ„B’q€´À-\"sb°ŠŒ€­u,/órµ¯Ğf„¥ãs‹\\CNs@qù„âšRÛF\Z Š¼82¶ÂÈ-Õ7Ö‹$‚“ÀI”FVægl8õœà•É<0?Y€3züÚ»ƒE­îåLÖÎ*$9Ï1~˜-¸Š,è:¤nì€Á¿„DM¶»)fêíFÔ€‘Cz.’ ƒ \0É-ÀJFÔ>:¢Ë1RÄ„zv>#%¤¶+7ûóø×\\0Wal\nšÌİPx\0ê‰•\"<§V`K$iqqËb‡Î!ÿIFïïçÒ`±-Fpfb‹2ä1ü‘Ä	¥„Ä>AiÒ;p‘ùÖÉUğgCø³c(e4;•8ğo²ä&M{#\rÊV˜• °]BO‡”Œì¬×È\'Ú®BÏ\\hõ©¡	F°$ØMÏÄaíyÎçT&³¡ÉlÅKÇ*ÊPQ†dn.fš ÁÉ<ÜÉçSIˆ;Fç‘_*‘S5ótÚ#h÷?À»tà§Á\\¶àh$HØĞ¯À†ÀB3Cù”S?ô+[ ¶Px2à¥s\Z³å–fòÆ¤397 Kƒ==·Ña\0Lí–B	\n¤:S›yÏq 0m¸ØcÄ‡»ğ3÷„¸€ò€€4P‹ã©<!¨ğË†øå	Aş#‰¹ãÅ€X„Ü-A@úp<EQL§Á\"ltrvr	 >/àîÙ“)oåœKe1?ÔbÊ—›j6vĞ¬ONØ&‘\rPC®JÁĞUì¯í)U\'èMÆÔÜ™ÎóÎ8íåñ­RĞ\rôéPÒO•Gûy\0»”i¿Î˜c¨ó5ÓğàiƒÀ€,ÙçÒ~ŸÄ©ñÑ#B8rçEŠ0Î|†½š½ÚêÎ:PşÎJ;àf“	˜íNS.D9dD mn£s¬%½E~n‚Ç.E^4`ĞM.°,Ç)%âásˆ\rVÉ€Qç¤­p×†¸kû3ƒ/g(18¾INUÅf6ŸˆYn+•nıPİ*%D;29@¨‹p>—\rœ½ªE\0\"“6	L(p†¹×Y\n]ùÚ‡ ,O†ãVw«1ûæz|\'‚‡ğ<Yb2¿¢\"òq	xnÖY¨eÁ\n–úÀâ‘+ÁÆ\\ø„ù¤¼dg¢ry\'•q¿ò®á]Ÿ\n’½¸ªyXºQœ†ÿc=‚II‡™ªLĞ½IIp…q­hÍÁ£.»ŞLëã®Ó¯e^¹Uå¦<Y6#³&JI\0É	ÙÕÕ•ÙèÂFÖ»“¤NÎ3/`‰å,q^%Uá‹N2MDùôğ‚Od„)]º\"P;YhZí¹ğ hzsÔšå½ßSmŞK³Lîèòò}7gŞUòjË’WØwê+§=Š¤?2•9m_àÿË¢­t÷“Kvå™ŞˆÇB7çïr²W–ş´-½½[G§B¸¦ºéû·Ï¸–Â…½GLm·Qix¢Gq¡SªaÓjôôª»Åíæéc¥]O[»¯–|Q‡Š:‰· ˜™äIÎŸnK,Òõ:ú´\rÿ”°Ù}v¶ç#­Ò½Â“rˆ9Qâ>„}ï#LéH9$…’MíïßşC‡®KÄÖ¿ˆbdœ“¸\n–U°œßÕn¯nêèİ6xâcØnxK7€X?}]¾b¨„ÂN‹à¼r^k[bé–æ„ïõ­¦6–yü\\åš©¨ö5ÎÖmnÎ™4MWÊí9ò\rüÚÔ\\”%õ#qõnÕöÿ\0\0ÿÿìVÍrÚ0~Ï\Z’–)ÌpÒÊxL_`±e[©,y¤uÜpÊƒ´/—\'ÉJ@ø	3ı9Q¦¬İeµúv¥İ/èt¦ã~çê:`ÍÀX‘ÆSA-qøÆí¨F›A÷\Zõ¼‚„OCÚâ$ı-¸Ø·Ì-é}m1yŸUz`´´™P9i!CnÜv´–BñaĞ»|âZ’ÂE_E3«ã˜[­Ğ:g›1¾Š’[6ã\r‹u	ÊmUÜ({Ü’Ø·Š]’ŸÇÖı°\ng—eGwA	tg ï:5>?\'s®f€ì{).¿”¹ÊpËÍFı6Ûì“6º¸l±y›9è¡x•Ñ:!ÀøX‘³­¸”sƒëä»zŸÒÑ\n%~F¨Òq¼\\-ôfÎ¢H1Ü[ş\r¨3­ÔóÓÛZ¥Pr… -Ó›è²ª©¯±™k#°(mëùéç<¹; D(àOjéš¥&ÔZüøÙ2Õÿ‰´?ÂÎi\"¢z!E(ô~8z1:ãî$ÿU9œ_¹ò¤Ç×L#gƒ_½$ã__,‚ŠºŸ°5[pf9²Z	d°œ~°`BQT YRh‘ğö^\Z6Ãò#ÇH^ÛÆúvøv’Ïck†A··æŒ­ûï‰?úçSå_ÀÓ]‘şrE+#¤[q¡u¹•%Ïv¬‡Ô±Óë§¤™¦ŠmÅ¼F/®Ã%šš³¡^ÎÅŸ\"ÕÉ\0;z	Lè”ï®¼•p® z\Z¶Ğé£_Kí¦Éè\0\0ÿÿ\0PK\0\0\0\0\0!\0–µ­â–\0\0P\0\0\0\0\0word/theme/theme1.xmlìYOoÛ6¿Øw toc\'v\ZuŠØ±›-MÄn‡i‰–ØP¢@ÒI}Úã€Ãºa‡Øm‡a[Ø¥û4Ù:lĞ¯°GR’ÅX^’6ØŠ­>$ùãûÿ©«×îÇ!)OÚ^ırÍC$ñy@“°íİö/­yH*œ˜ñ„´½)‘Şµ÷ß»Š×UDb‚`}\"×qÛ‹”J×—–¤ÃX^æ)I`nÌEŒ¼Šp)øèÆli¹V[]Š1M<”àÈŞ\Z©OĞP“ô6râ=¯‰’zÀgb Ig…ÁuSÙebÖö€OÀ†ä¾òÃRÁDÛ«™Ÿ·´qu	¯g‹˜Z°¶´®o~ÙºlAp°lxŠpT0­÷­+[}`j×ëõº½zAÏ\0°ïƒ¦V–2ÍF­ŞÉi–@öqv·Ö¬5\\|‰şÊœÌ­N§Óle²X¢dsøµÚjcsÙÁÅ7çğÎf·»êà\rÈâWçğı+­Õ†‹7 ˆÑä`­ÚïgÔÈ˜³íJø\ZÀ×j|†‚h(¢K³óD-Šµßã¢\0\rdXÑ©iJÆØ‡(îâx$(Öğ:Á¥;äË¹!ÍI_ĞTµ½S1£÷êù÷¯?EÇ?øéøáÃã?ZBÎªmœ„åU/¿ıìÏÇ£?~óòÑÕxYÆÿúÃ\'¿üüy5Òg&Î‹/ŸüöìÉ‹¯>ıı»GğMGeøÆD¢›äíó3Vq%\'#q¾ÃÓòŠÍ$”8ÁšKıŠôÍ)f™w9:Äµàå£\nx}rÏx‰‰¢œw¢ØîrÎ:\\TZaGó*™y8IÂjæbRÆíc|XÅ»‹Ç¿½I\nu3KGñnD1÷NIBÒsü€\níîRêØu—ú‚K>Vè.EL+M2¤#\'šf‹¶i~™Véşvl³{u8«Òz‹ºHÈ\nÌ*„æ˜ñ:(W‘â˜•\r~«¨JÈÁTøe\\O*ğtHG½€HYµæ–\0}KNßÁP±*İ¾Ë¦±‹ŠTÑ¼9/#·øA7ÂqZ…Ğ$*c?¢íqUßån†èwğNºû%»O¯·ièˆ4=3Ú—Pª\nÓäïÊ1£Pm\\\\9†øâëÇ‘õ¶âMØ“ª2aûDù]„;Yt»\\ôí¯¹[x’ìóùç]É}Wr½ÿ|É]”Ïg-´³Ú\neW÷\r¶)6-r¼°CSÆjÊÈ\rišd	ûDĞ‡A½ÎœIqbJ#xÌêºƒ6kàê#ª¢A„Sh°ë&ÊŒt(QÊ%ìÌp%m‡&]ÙcaSl=XíòÀ¯èáü\\P1»MhŸ9£Mà¬ÌV®dDAí×aV×B™[İˆfJÃ­P|8¯\ZÖ„AÛV^…ó¹f\rÌH ín÷ŞÜ-Æé\"á€d>ÒzÏû¨nœ”ÇŠ¹	€Ø©ğ‘>äbµ·–&ûÜÎâ¤2»Æv¹÷ŞÄKyÏ¼¤óöD:²¤œœ,AGm¯Õ\\nzÈÇiÛÃ™ã¼.uÏ‡YC¾6ìOMf“å3o¶rÅÜ$¨Ã5…µûœÂNH…T[XF64ÌT,Ñœ¬üËM0ëE)`#ı5¤XYƒ`ø×¤\0;º®%ã1ñUÙÙ¥m;ûš•R>QD¢àØDìcp¿UĞ\' ®&LEĞ/p¦­m¦Üâœ%]ùöÊàì8fi„³r«S4Ïd7y\\È`ŞJân•²åÎ¯ŠIùR¥Æÿ3Uô~7+ö€×¸#¯mq¨BiDı¾€ÆÁÔˆ¸‹…i*¸L6ÿ9ÔÿmÎY\Z&­áÀ§öiˆ…ıHE‚=(K&úN!VÏö.K’e„LD•Ä•©{D	ê\Z¸ª÷vEê¦šdeÀàNÆŸûeĞ(ÔMN9ßœ\ZRì½6şéÎÇ&3(åÖaÓĞäö/D¬ØUíz³<ß{ËŠè‰Y›ÕÈ³˜•¶‚V–ö¯)Â9·Z[±æ4^næÂç5†Á¢!Já¾é?°ÿQá3ûeBo¨C¾µÁ‡MÂ¢ú’m<.vp“´Á¤IYÓf­“¶Z¾Y_p§[ğ=al-ÙYü}NcÍ™ËÎÉÅ‹4vfaÇÖvl¡©Á³\'S†ÆùAÆ8Æ|Ò*uâ£{àè-¸ßŸ0%M0Á7%¡õ˜<€ä·ÍÒ¿\0\0\0ÿÿ\0PK\0\0\0\0\0!\0š)RœÖ\0\0J\0\0\0\0\0word/settings.xmlœUÛn›@}¯Ô@<×;vR¡àHvê^ä´UI>`€VÙ›v÷ë;lHÚ(ŠúäİsfÎÎ_^=p‰6TŠ4\\Ìã0 ¢%u\ZŞİîgÃÀX%0)H\Zˆ	¯6ïß]v‰!Ö¢™	PB˜D¦a«EbŠ†p03N-¬ì¬<‘UE2ş„£‡NÃÆZ•DÑè4—ŠT«¤æ`Í\\ê:\Z<¯eÑr\"l´ŒãóH6\rUÆ«ñÿUÃ§\Z/r|-‰#gŞ®[Ä¯YévR—o	Ï9(-bV–³!]TxÃŞ¢3Ôó@s\rúôDdƒmû-%ºD]`A±çqFÀ‡e•Y°i£cıŒ\0>ß%µÎ›6 ½OI*h™½…<³R¡Ñ0À‹å(Y4 ¡°Dg\n\nTÛIaµdŞ®”ß¥İI®4&<Ã¢ÀºpZCöŸp’­Å§¢.™(œÖÒ8wø%¥õ‚q|½]Ççƒ–c\'&Ş.vŸ¶/1“¾2jóÄÅO½¹N{Œ<àCz;à¹¦Ü¸ÁÁØx’ëû-Ï	0yÊdmîÉÙl ÆöXOàÌLIº&U/Ìn@×“r_VèQìÅ·G5×[¢?kÙªAµÓ ¾Šaÿàbµ\Zõ¨°Ê=nÚ<ó^çç	ÕŠòÇQ;Áh*P—X\\yâ*t\0QûŠ1»Ëœ)véÌ}È\r(…c€&y½HCFëÆ.ÜlY¼• ïûK^/GnÙsxs\\Âe†ÖãÁG´\Zvæ±³	[yl5ak­\'ìÜcçkN¸0¸÷¸~şèğJ2&;R~ñ`\Zş\rE0\r(‚}uû‚Ã+“ÈÇ„<à6’’Züâ*ZrxHÃe¼î{4Z³~Ù:%g¬¡A	p·ûV=sîWé¯Xº¤$ÅÌN<ŸÖs>Î¨±Q¸ÉVjL¹_ñ½òô\'°ù\0\0ÿÿ\0PK\0\0\0\0\0!\0ocP›‹\0\0\0\0\0\0\0word/fontTable.xml¤’ßn‚0Æï—ìHï\'ÿLhœÓË],îX¤	mIO•ùö;Päbf‰d4á;íÇéï|‹Õ·*‚‹°(NX4à,:5G©O	ûÚï^¦,@ú…Ñ\"aWlµ|~ZTóÌh‡×8·	Ë+çaˆi.àÀ”BS-3V£O{\nM–ÉT¼›ô¬„vaÌù$´¢\0GÿÆ\\–ÈZ·ê·ÊØciM*©YUx?R³eÛ]PÍ5(êz…<XÙJĞEDµ	ã1ßñ1­õ;âÃzeaíæ`Q¸n#÷rJ×›Š•Dô…Rº4¿é°…ğ%”\'*œñÀ¶8çñnÇ¼%lDÂzÓ)15åŸY»gØ)4j¬ñi¶D³Æ‡òiO5}†~>w$öR	>D|\ZÕ=‘˜OˆÄ˜xÔd†½ˆØÆ·!ø(j<^w÷§›lHy¢öş½ˆxŸÇ‰l@Q4àlÔ<‰šH¿lô\'±®GoeƒóÑÛ‰&	”¨ÿd£\r	.\0\0\0ÿÿ\0PK\0\0\0\0\0!\0JØŠ’»\0\0\0\0\0\0\0\0word/webSettings.xmlŒÎÁjÃ0Æñ{aït_õ0JHR(£/Ğõ\\Gi±d$mŞöô5l—İzŸøñï_im>Q42\rğ²m¡A\n<Eº\rpy?=ï¡Qó4ù•	øF…Ãø´éKWğzF³ú©MUH;`1Ës\ZL^·œ‘ê6³$oõ”›ãyß8|$$s»¶}u‚«·Z KÌ\nZyD+,S¨ZCÒúë%	ÆÚÈÙbŠ?xb9\nEqcïşµw\0\0\0ÿÿ\0PK\0\0\0\0\0!\0OT1x\0\0É\0\0\0docProps/app.xml ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0œRËNÃ0¼#ñQî­Ó¢BUm]¡\"Ä—ÔĞ-g“X8¶e»ˆş=kBÛ nø´;kgÆ†Õg§³ôAY³Ì\'ã\"ÏĞH[)Ó,ó·ò~4Ï³…©„¶—ùC¾â—ğê­C†Œ(LXæmŒnÁX-v\"ŒilhR[ß‰H­o˜­k%ñÎÊ}‡&²iQ\\3üŒh*¬FîD˜÷Œ‹ø_ÒÊÊ¤/lËƒ#ÁJìœùs’£\0(mºTò‚àS¯¢ÁÀ\'ÀúvÖWOon€õ%¬[á…ŒŸÌæS`\0nÓJŠH¹ò\'%½\r¶ÙËwY\"\06Ü”ÊåŞ«xHB†-<*“¤\\ë+ÒæEã…k\'pĞÁF\nkòÎk¡;°¶æÀIè±\"¾÷ğæJ{—Âù9òØÜ©ØnœIÌ|v=4<Á†rÁŠ	Ï\0<Ğƒxn¥°LƒÕqÏßAŠpÛÿL>™Zß™12~ú2ü\0\0ÿÿ\0PK\0\0\0\0\0!\0ŒÑâyq\0\0ß\0\0\0docProps/core.xml ¢( \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0œ’MOÃ0†ïHü‡*÷6M\'ªÚN4.LBbÄ-$ŞÖ&Q’­Û¿\'ıX·\nNÜbûõûM²Ù¡*ƒ=+”Ì‰b€dŠ¹ÎÑÛrŞ¡À:*9-•„Á¢Yq}•12eàÅ(\rÆ	°\'I›2£s:ÅØ²\rTÔF^!}q¥LEÍ\ZkÊ¶t\r8‰ã)®ÀQNÅ\r0ÔõHÎ¤Ş™²p†¡„\n¤³˜DŸµLeÿlh+ÊJ¸£ö;õã^²9ëŠƒú`Å ¬ë:ª\'í~~‚?Ï¯íª¡WP‘q–:áJ(2|>ú“İ}}s]z| N™âI•»¶ç”h¬ŞÂ±V†[ß6Š|ËŒĞÎ?`%¼º¤Ö-ü‹®ğûcÏÿo®1°ÍO(’ö!ôÛ´æuC¼igŞ©ò>yx\\ÎQ‘Äd&7K2M“Û4?›uFı=]¢êû7ñèœÉâ\0\0ÿÿ\0PK\0\0\0\0\0!\0M3ç\0\0á9\0\0\0\0\0word/styles.xml´›ßsÓ8Çßoæşß!mÉÑ!0¥ÀÑà\niç[i48VÎRhË_ÒÊV]»¶wkñDıCûÙÕ®¾ëéõÛÛ]ıä…2_ÆÇÏâˆç‰LE~½Œ¯.?>û+”fyÊ2™óe|ÇUüöÍŸ¼¾9Qú.ã*2ruR,ã­Öû“ÉD%[¾cê¹ÜóÜ<ÛÈbÇ´¹,®\'r³	/“Ãçz2=:šO\n1màj+ö*.­İ`¬İÈ\"İ2áJow™³·c\"ß÷R™¼çvÈ´²—ÅEQ^–WğÏG™kİœ0•qi7!îD.‹O§¹±yÂ™Ò§J°úÃå=û|k_¬?ô#¥kß‰TÄU¿Ì°Ÿ,[ÆÓiuçÌ:ñà^ÆòëêÏŸ]­êÎ,ckmì.cV<[Zcˆ´ú·ñşAüæ\n\\Ù³ÄÌá°æ&‡&%–“	›ëéb^]|?dæ;hYBÀ€ÕÍšËÆ¤›ÔšD¯\\¡˜§|óY&?xºÒæÁ2–¹yu~QY}·Œ_½²LssÅwâ“HSnë²¼w•oEÊÿİòüJñôşş·Pe¥ÅDrmÜŸ/ 2•~¸MøŞV™13›ä¯v@fÍª\Z:ˆ{oÜnşW!]¥l9³+)ÿ{Aõa4hj#ª\0vI¾ÎÆ›x1ŞÄËñ& xÇÍÅb¼F?ÇfÄÕF­*ñIÕ2qÅWŸ‡Ù«’µ#ZU48¢U4ƒ#Z528¢Uƒ#Z08¢•ğÁ­üh¥³wDÂ@¸šU4ƒÙ@-ìK¡3nÇ÷\nĞñH©+[MtÁ\nv]°ı6²½µévŸX®ksäôéb¹Ò…Ì¯gÄtg»tŸ¬Évû-SÂ|ÔLıtäÔ_²uÆ£¿‘¢^ºâkÅ&¶°‹Œ%|+³”Ñ%¿u%Œÿ*£•ûÊtndZ?‹ë­V[h¹ƒ°yÇ¤wÏ„³ÿY(˜ƒŞÅ4ïeÈ8*‡óºì6ş…§â°«¦ñ52wzNHs.öOÑ›¢öê\ZŒÂ&\0‚kôÀ>Â×\\èöm1ş»VôDûÿ]ãz¢}¨şü’•æ=+~D¨åµ ¯İ3™ÉbsÈª50(ò\nö\\äEìí£DbA^Áä3:Mó›¦NÉ¹¸×Q…œGÅ†…œ”†ì\"\"\'¨ÁšXã´–\0\"‹îwşSØ¿=Q›¨´ÿÖ\\Î³0-õ\rıí õğ7ô´Có°”óÜü¹DñG›u¬<,­¬\'×ï9×ø q\0\Z×\n	 úèşæñ=ß	,²,û.e‡VæY™=ˆÖõMÄ÷WÇêí®…vßDPÈ	j÷M…œF/ó}Á\nÖ7¬®Ñ£º¦R‚\"÷Í:È	 \"\n#ŞPñF€Âˆ74^¼‡!áÄÁ\"kƒ×Ôºx#@ğ\nåW}ª‹7DÖ§våßŒª¾Vú¹\r Ş\n9AmñFPÈÙéo^¡TBƒå¥Á\n#ŞPñF€Âˆ7F¼ 0â\0ïaH8ñF°ÈÚà5µ.ŞY<¨.Ş¼BÑ†GÅVıoo…œ ¶x#(äì4Õ¤\"Xä5X^¼,x…R%Š›TñFDF¼ 0â\0…oh¼xCÂ‰7‚EÖ¯©uñF€ÈòàAuñF€ÈÚğ¨xÃbüíâ Ôo…œ† zC°È	j°¼x#XP/£Å‚W\n¢DF¼…o(Œx#@ãÅ{N¼,²6xM­‹7D–ª‹7DÖ†GÅÖÈoo…œ ¶x#(äì4Õ‹7‚ENPƒå¥Á\n#ŞæhñF€à•\'€`QÒF¼…oh¼xCÂ‰7‚EÖ¯©uñF€ÈòàAuñF€ÈÚ`÷Ùšı¢èí©ÇE€İgPíj@§IÂË\0¿ó\r/Ìa&>¼;d$°Š@ì(lˆï¤üá6vÏ:\nëLHØÒ}»tjf‹“—ÿœEŸÜ˜Ö8(©‡;oÌé¡úq!8d?õİŞÙÙW;Ë­5s@Èí*\0ÁQ´ss ¨<ÖcÛs>æE8TUŞ†ÿ·-©ægC„mT²5¬ÄœˆêA•Şı$ØîŞwìŠGîdTn–»ãï¿¡Ü{öhöú­íNğŸa§xïEğŠËjÛAs8\\\ZòĞïª‚·õ:sÇÍÌçyj¢5çûàĞ\\bÓ[æÌšçg<Ë¾08œ¦å¾ûÕŒo´{z|İ°aj-µ–»îñlO3`J£îŒ»´At×L~Ø­yaN{õÌÿWi»œJ{Xnß«K·__Æ{¨^ì¬ßûVı¤Şü\0\0ÿÿ\0PK-\0\0\0\0\0\0!\0İü•7f\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0[Content_Types].xmlPK-\0\0\0\0\0\0!\0‘\Z·ó\0\0\0N\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ÿ\0\0_rels/.relsPK-\0\0\0\0\0\0!\0Öd³Qú\0\0\01\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ã\0\0word/_rels/document.xml.relsPK-\0\0\0\0\0\0!\0‘_.ù\n\0\0÷i\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿ\0\0word/document.xmlPK-\0\0\0\0\0\0!\0–µ­â–\0\0P\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0I\0\0word/theme/theme1.xmlPK-\0\0\0\0\0\0!\0š)RœÖ\0\0J\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\Z\0\0word/settings.xmlPK-\0\0\0\0\0\0!\0ocP›‹\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0word/fontTable.xmlPK-\0\0\0\0\0\0!\0JØŠ’»\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ò\0\0word/webSettings.xmlPK-\0\0\0\0\0\0!\0OT1x\0\0É\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¿\0\0docProps/app.xmlPK-\0\0\0\0\0\0!\0ŒÑâyq\0\0ß\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0m\"\0\0docProps/core.xmlPK-\0\0\0\0\0\0!\0M3ç\0\0á9\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0%\0\0word/styles.xmlPK\0\0\0\0\0\0Á\0\0H,\0\0\0\0','MCA404.docx',0,'MCA',4),('MCA405','Java Programming and Technologies',NULL,NULL,0,'MCA',4),('MCA405L','Java Programming Lab',NULL,NULL,4,'MCA',4),('MCA406','Minor Project I',NULL,NULL,1,'MCA',4),('MCA407','Computer Graphics Lab',NULL,NULL,2,'MCA',4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
