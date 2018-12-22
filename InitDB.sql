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

insert  into `subject`(`SCODE`,`SNAME`,`SYLLABUS`,`FILENAME`,`TYPE`,`COURSE`,`SEMESTER`) values ('MCA101','Information Technology',NULL,'NA',0,'MCA',1),('MCA102','Mathematical Foundation of Computer Science',NULL,'NA',0,'MCA',1),('MCA103','C Programing Language',NULL,'NA',0,'MCA',1),('MCA104','Computer Architecture and Assembly Level Programing',NULL,'NA',0,'MCA',1),('MCA105','Communication Skills',NULL,'NA',0,'MCA',1),('MCA106','C Programing Lab',NULL,'NA',0,'MCA',1),('MCA107','8086 Programing lab',NULL,'NA',2,'MCA',1),('MCA401','Artificial Intelligence and Applications',NULL,NULL,0,'MCA',4),('MCA402','Mobile Communications',NULL,NULL,0,'MCA',4),('MCA403','Computer Graphics and Multimedia','PK\0\0\0\0\0!\0���7f\0\0 \0\0\0[Content_Types].xml �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�T�n�0�W�?D�V������8�H�`�\rX�K����nDUA*�)Y����ă�ښl	1i�J�/z,\'��nV���K~ϲ��)a���m ����j0�Hu�T�9bx�<�9X�\n�Q���\n��8�A�O1~���q���k6<A%��5}�*�`��k�����I)_:��%�1�ٜIs�\r�`� C]9N��{#k�V��E�WaI_����rai���N_UZB�_���%�D�[S�+���?�#��@�[�.z�9�>$N{9���+P9Y ��vu�GD����oR��w�ͳ�\r�Iʊ~���\Z8��W�Z�\"V0}����������>����uQwHo��\0\0\0��\0PK\0\0\0\0\0!\0�\Z��\0\0\0N\0\0\0_rels/.rels �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0���JA���a�}7�\n\"���H�w\"����w̤ھ�� �P�^����O֛���;�<�aYՠ؛`G�kxm��PY�[��g\rGΰino�/<���<�1��ⳆA$>\"f3��\\�ȾT��I	S����������W����Y\rig�@��X6_�]7\Z~\nf��ˉ�ao�.b*lI�r�j)�,\Zl0�%��b�\n6�i���D�_���,	�	���|u�Z^t٢yǯ;!Y,}{�C��/h>\0\0��\0PK\0\0\0\0\0!\0�d�Q�\0\0\01\0\0\0word/_rels/document.xml.rels �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0���j�0���{-;���ȹ�@���\0�����$���~�\nCR���`Fh擴��wo�\'�U�%)���:�(x/vw� ����qH��oo6�h4�C�v�DL���e�k)�l�ה8�6��.��������\r�U�>�0̀�\"S�+a_݃(���vuݕ���c���T�/<�!s��X\Zd3�� �����?\'g![�?��4���%�9����R�k6��$C�,�`&g�!/=�\0\0��\0PK\0\0\0\0\0!\0��Ţ\0\0��\0\0\0\0\0word/document.xml�]�r�6\Z�ߙ}�.v�3�,�I�ȵ:�e\'�T��v��%LB\"j���T�*�Ы��\\�d?�%�tt�&�X2(@���?�ǟ��\\2eRq�7Z�a�%l�O��^���� *��M]��Ɯ��O�����Y�V�1?$����Su�0���+�aU-0�Bz4Ŀr��Qy{��\Z�k��p��i��o��ǍH���{��Pb�S�b<�K?g�M�O9��d.�A���Z\\���j������K��z��w�`��ْΰ��<�LH;��bJa��̮x��ҽ��/����#��x�r?����[�-^����{__j9��h�Z�s��Y�h_7���΋��Nc1�gc\Z���#����\"#�?h�ˀZ��KL���5��G��>b����O�p�۷*\\o�t2��e�u��y�Y��E�b@�=��LG�?T�deq~ܸ�S����Q��Y�9�U�K�=!��u��Tş�#�ϴs��\\}<��\\���O�� }2�NJ���n����=k�S�/���4p���?��!�/��TO*���N0&����]jۚ�^ޥ�d�L4��)5���np�70��2h)uֿ�ǔ:�<hB ]-�!���S��eB\'G+��R��.�<�)I�ː�0��_]�n9�^&5��L�̷K:��u\"6s�k{�I,��8���w��8	7QM�gS� 6`Ř��ҞQ���^�����{[�~�R��PنZ�B�z\\�ɋ��r�s�����\n\\:�Mᑁ�4K�P�|k�\n���;L�����ԏ��{K��vtv��]���6�u�*��I��#2���H�N����+\\��a\\��/H�be���22a>�Q�=�#��Ts�R+�W�@��}����h:�1ͪԋ�q����FS��Y1c��f��\n��G���M�\0��T��!�D�\Z(���.�w���9��\"�溑g;ʳ�{p�r!D�?�C4 �]h�����y.��0��\Za\nE\0+�(�~o �hj�e/BHu�2�T�2v[`$2�y��HFɘC@!֔��yiQ?9=5n�_���k!�/�4�4ː�+�sϒu�`���!�.�:�n�b��r�s�,�����j��� 68Q��%���J�Ht��Q2�dohD`�ް޴�F���F�y��h�I�c0���e@مO�Ø��e����;�[��\"�Ćya9T����Cm�PK\\}�^*9O��A�~w�z���n��O�����A(f���c��6ϥ��=g:O*)�ܧ�\0���m1k�)\"$C�� ���A���[�9���D�\'w\'�Y�%�U���`���$�r�|`���2B�_��a��/w>��`����5��8�X_�����3bm��~���K����dvn�F��Rܐx� �4�)�i�����>2.\n�`[d6E�.K�p�8��=�iR+F�qt�ٺ��t�\Z�6���<a3�K~�eQD�H��6�#��W�u�V��:<��4���j���Re�!�Rĺ6!0�$qun�2�����9*�c�ά���!pu�+��j�C�X���tKW�L�s/M�Bt�E����\r#�_5�o�7���7�.\\�\',��]� owhu��T\0~�v�!}T����60����$ŀ��ʑ��&1X·X�o��(�Zf=�Òŕ�~����݊P�j@�R۬�R,U�*[[\"TE���\"^�\'�N��yc�\"5EU-\n��G�d}���,��$��r��>����^(�Er�F�\\z��S����xI�2�����D�jUP9�$�d;�H�#��!��w��?8X	+�{����d��;�enځb��l�\"uр�ɷEB�nۈ��FP\\]לH�	\\�\\S�h��}<�Dc� t�u4���.�G�lZk�\n�<8R��W,H#�w��tb��\'i�CN�)F\n;J�ؑ\r�e��+�u�z�.�h��F�zܻ	r���&�Ϛdx�o�3��v��VP���^�_�w�����=�,��Q\'�Hړ�I.�Λ�jp���A<���k�G B2��.���qaح��L�Q�t�x~O�J�z��	�6ѵ	��E$el�F�>᫶��l�W�����H���Lы�\Zdv�[o�~���j�vh�e�\Z��+z������������?;��Fo`%�P=�y+\n�v�gX�D�&���\0*�*��UDrwZ䲥��jc2D���OJ�G(I�aJ$6��N\0���c��J�r���(�y��Z���{C�5�yq��f#�Vb�nd�~�՞��mq����`�FT�bѕ��/!K�U��b������⁛��ӥ$���\Z��w��w�1\n�(ܲ�W7\n�쳶ؘ���j�ی-�˧������\n��>��A},����!����Lo��t���k݈\n��(yk�Xsƚ[Zs��q-ik�m1�h���uiG�b=�����o�s�ym��@�����&�yI��(������/��>����/�S�h&��p���\Z�l�r׶��#��3��IfL��IV̫/�U���ũ� }O�	���6��0�\Z��-u_btA\"��N�l�!��!^�B�h��-b|T��I���j��xHѢ]]�0@Q�S>��\r�uۃ3�E:��҈�6m�!\'�*���ƕ�E�Q�u�E�^;���|���\n�������4���Rr��XK��z˱�O=�h�Y��n�r�MT�Ƙ��Wʜ.�*d^�]&�y!5��&�$�vU�\Z��F\\\r��\\��K�T%�f ��8��R7�	rI^1A娩���P����\n��m4��z�=�>��r��*=zr�Fv\"��O˩C�ƀ����f�>�p0�\nĢ�(����os�\'��_�\\\n���~����Rq������Ǎv����켳82Z����{��6�뮓0+���\"��#\Z ���ir�\r�YH\"�Ռ�������(�#���d���\0\0��L�[�0E�b����h�	�@[�	0������Ν;w.�J`iv.5V�G���|�ItaE�ҩ}��r9�s���#NN���p��a�����bt��*k@�aսj6n�*�LΎ܏o\0p#�����[�f�J�%⯐ nFKrz=�R��+��������@�yP#\0\0\0��\0PK\0\0\0\0\0!\0����\0\0P\0\0\0\0\0word/theme/theme1.xml�YOo�6��w toc\'v\Zu�ر�-M�n�i���P�@�I}��úa��m�a[�إ�4�:lЯ�GR��X^�6؊�>$���������!)O�^�r�C$�y@�����/�yH*��񄴽)�޵��߻��UDb�`}\"�qۋ�Jח���X^�)I`n�E���p)���li�V[]�1M<����\Z��O�P��6r�=���z�gb�Ig��u��S�eb���O������R�D۫����qu	�g��Z����o~ٺlAp�lx�pT0���+[}`j�����zA�\0�V�2�F���i�@�q�v�֬5\\|��ʜ̭N��le�X�ds���jcs����7����f����\r��W���+�Ն�7����`���g�Ș��J�\Z��j|��h(�K��D-����\0\rdX��iJ�؇(��x$(��:��;�˹!�I_�T��S1�������?E��?������?ZBΪm���U/������?�~����xY����\'���y5�g&΋/����ɋ�>���G�M�Ge���D�����3Vq%\'#q�����$�8��K�����)f�w9:ĵ��\nx}r�x����w���r�:\\TZaG�*�y8I�j�bR��c|XŻ�ǿ�I\nu3KG�nD1�NIB�s���\n��R��u���K>V�.EL+M2�#\'�f��i~�V��vl�{u8��z��H�\n�*���:�(W�☕\r~��J��T�e\\O*�tHG��HY��\0}KN��P�*ݾ˦���TѼ�9/#��A7�qZ��$*c?���qU��n��w�N��%��O��i�4=3ڗP��\n����1�P�m\\\\9���������Mؓ�2a�D�]�;Yt�\\��[x������]�}Wr��|�]��g-���\neW�\r�)6-r��CS�j��\ri�d	�DЇA�ΜIqbJ#x�꺃6k���#��A�Sh��&ʌt(Q�%��p%m��&]�caSl=�X�������\\P�1�Mh�9�M��V�dDA��aV�B��[݈fJ�íP|8�\ZքA�V^��f\r�H��n���-��\"�d>�z���n��Ǌ�	�ة�>�b���&����2��v����Kyϼ���D:����,AGm��\\nz��i�Ù��.uχYC�6�OMf��3o�r��$��5�����NH�T[XF64�T,ќ���M0�E)`#�5�XY�`�פ\0;��%�1�U�٥m;���R>QD����D�cp�U�\'��&LE�/p���m���%]�����8fi��r�S4�d7y\\�`�J�n���ί�I�R���3U�~7+���׸#��m�q�BiD����������i*�L6�9��m�Y\Z&�����i���HE��=(K&�N!V��.K�e�LD�ĕ�{D	�\Z���vEꦚde��NƟ��e�(�MN9ߜ\ZR�6����&3(��a����/D��U�z�<�{ˊ�Y��ȳ����V���)�9�Z[��4^n��5���!J��?��Q�3�eBo�C����M����m<�.�vp�����IY�f���Z�Y_p�[�=al-�Y�}Nc͙���ŋ4vfa��vl����\'S���A�8�|�*u�{��-�ߟ0%M0�7%����<���ҍ�\0\0\0��\0PK\0\0\0\0\0!\0��Z�\0\0J\0\0\0\0\0word/settings.xml�U�n�@}��@<�;iQp�:q/rڪ$0���M������6$mU}��93g�/.8D*E.O�0 ��M������X0)H�	/7o�\\��!֢�	PB�Tfa�Ejʖp0NK-���<�uMK2�������Z�F��t\"�VK�����h���eǉ�Q�g�&,lZ��W����O�^��Zμ]��_����=�%<破,�1XY��t9P�e����{Zh��\'\"l�/)yЧ���=��0r>,�܂%HE��d��>m4pش|*RC��-��\n���\'�dق���+(Qm+�Ւy�J~�v+�Ҙ������]��(;�OE}:S8��q6��SJ��x��_�Q˱3��*Y�n_bf|e���zs1�vy�����M!�q��������|Ap��S&�\nO.#a80���xgfd*j��av������T��b/�>����I�N����ET���դG��S�q���8?O�NT��	Fs�����W�=��W���]�L�sL��@n@)4)�e2ڴv�f��}?\\�&��d���������3�h5f��c�3���j��[�ؙ���qap!�q����dL�����,�\Z�`ZP����W�0-�	)y�m$���U���I�z4Y�a��:%g���Ap��V=sV�X��\"%Ł̏����d�Qcs�p��Ԙ�����O`�\0\0��\0PK\0\0\0\0\0!\0ocP��\0\0\0\0\0\0\0word/fontTable.xml���n�0���H�\'�L�h���],��X�	mIO���;P�bf�d�4�;����|�շ*���(�NX4�,:5G�O	���^�,@���\"aW�l�|~ZT��h���8�	˝+�a�i.���BS-3V��O{\nM��T�����va��$��\0G��\\��Z�����ciM*�YUx?R�e�]P�5(�z�<X�J�ED�	�1��1��;��zea��`Q�n#�rJכ��D�R�4�����%�\'*�����8��nǼ%lD�z�)15�Y�g�)4j��i�D�Ƈ�iO5}�~>w$�R	>D|\Z�=��O�Ęx�d����Ʒ!�(j<^w���lHy�������x�ǉl@Q4��l�<��H�l�\'��Goe�����&	���d�\r	.\0\0\0��\0PK\0\0\0\0\0!\0J؊��\0\0\0\0\0\0\0\0word/webSettings.xml���j�0��{a�t_��0JHR(�/��\\Gi�d$m���5l��z����_im>Q42\r�m�A\n<E�\rpy?=�Q�4��	�F�����KW�zF���MUH;`1˝s\ZL^����6�$o����y��8|$$s��}u���Z�K�\nZyD+,S�ZC���%	����b�?xb9\nEqc����w\0\0\0��\0PK\0\0\0\0\0!\05\nVKz\0\0�\0\0\0docProps/app.xml �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�R�N�0�#�Q��iP+@[#T�8��\0g��$�m�.�Ϻ�!�>���㙱��������Y��Y�gh���iW�Kuwv�g!\nSm\r��=�������[�>*Q��ʻ�cAv؋0���Ic}/\"��e�i��[+w=��ʢX2��hj���H��W񿤵�I_x���s��wZD�OI�6P�(t�z��c�b�s`Co�ׁ�_.�\r%�;ᅌ/�e	l��sZI)W�����61{>$�%`�-@�lQ�$d�2I���\"m^�^�.�E8v��B㚼�F��~\0X��	��$�X�{xq��M�|�\rNl���m��$�\\.Χ�\'#�R.X��#�\0�� ^�[),�b}��w�\"|~&�����!�#F��/ÿ\0\0\0��\0PK\0\0\0\0\0!\0���q\0\0�\0\0\0docProps/core.xml �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0���N�0E�H�C�}�$��TT6TB������4~�v��8���`��3s�x����[T���J(�b���q�-��zޢ�:\"���5`Ѣ��ʩΨ2�l��8����ͨ.��9�al���WH_�(#���bM�l�q<�a��C=рdtDꃩ:\0�* ��I��ց�φ�r��5��4�{�f�/��o�Ga]�Q=����\'�}��ҭ\Zr�zE�9��㮂2��?���Pק����)Uu�zN���=4�2���I��Xj�v�{�$���n�_tÁ�5�w������?�L�{��oә�	,�vd�y�����a�De\Z\'�0I��f�̳t���G�Τ���O�a�O�ޙ�,\0\0\0��\0PK\0\0\0\0\0!\0�M3�\0\0�9\0\0\0\0\0word/styles.xml���s�8��o����!m��!0����\ni�[i48V�Rh�_��V]��wk�D�C��ծ������]��2_��Ϗ��LE~���.?>�+��fy�2��e|�U��͟��9Q�.�*2ruR,�����D%[�c����<��bǴ�,�\'r�	/�Î�z2=:�O\n�1m�j+�*.��`���\"�2�Jow���c\"���R���vȴ���EQ^�W��G�kݜ0�qi7!�D.�O���yҧJ����=�|k_�?�#�k߉T�U�̰�,[��iu��:��^����ϟ]���,ckm�.cV<[�Zc������A��\n\\ٳ�̝ᰍ�&�&%��	���b^]|?d�;hYB����͚�Ƥ�ԚD�\\���|�Y&?x����2��yu~QY}��_��Lss�w�HSn벼w�oE�����J������Pe��Drmܟ/�2�~�M��V�1�3��v@fͪ\Z:�{o܍n�W!�]�l9�+)�{A�a4hj#�\0vI��ƛx1����&�x���b�F?�f��F�*�I�2q�W��٫���#ZU48�U4�#Z528�U�#Z08�������h��wD�@��U4��@-�K�3n��\n��H�+[Mt�\nv]��6����v�X�k�s���b�̯҅g�tg�t���v�-S�|�L�t��_�uƣ���^��k�&�����%|+���%�u%��*����tndZ?�뭎V[h���yǤwτ��Y(����4�e�8*���6���Ⱛ��52wzNHs.�O�����\Z��&\0�k��>��\\��m�1��V�D��]�z�}�������=+~D�� ��3��bsȪ50(�\n�\\�E��DbA^��3:M��Nɹ��Q��G�ņ������\"\"\'���X㴖\0\"��w�Sؿ=Q�����\\γ�0-�\r�� ��7��C�����D�G�u�<,��\'��9���q�\0\Z�\n	�������=�	,�,�.e�V�Y�=���M��W���v�DP�	j�M���F/�}�\n�7���ѝ���R�\"��:�	 \"\n#�P�F�74^��!���\"k��Ժx#@�\n�W}��7D��v�ߌ��V��\r �\n9Am�FP���o^�TB���\n#�P�F�7F��0�\0��aH8�F����5�.�Y<�.��BцG�V�oo����x#(��4��\"X�5X^�,x�R%��T�FDF��0�\0�oh�xC7�E���u�F����Au�F����x�b��⍠��o�����z�C��	j��x#XP/���W�\n�DF��o(�x#@��{N�,�6xM��7D���7DֆG���oo����x#(��4Ջ7�ENP���\n#��h�F���\'�`Q�F��oh�xC7�E���u�F����Au�F���`�ٚ�����E��gP�j@�I��\0��\r/�a&>�;d$���@�(l����6v�:\n��LH��}�tjf������E����8(��;o���q!8�d?�����W;˭5s@��*�\0�Q�ss �<�c�s>�E8TUކ��-��gC��mT�5�Ĝ��A���$���w�G�dTn���￡�{�h����N��a�x�E���j�As8\\\Z��廒��:s����yj�5����\\b�[�̚�g<˾08����Ռo�{z|ݰaj-�����lO3`J��At�L~حyaN{���Wi��J{X�n߫K�__�{�^���V����\0\0��\0PK-\0\0\0\0\0\0!\0���7f\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0[Content_Types].xmlPK-\0\0\0\0\0\0!\0�\Z��\0\0\0N\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0_rels/.relsPK-\0\0\0\0\0\0!\0�d�Q�\0\0\01\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0word/_rels/document.xml.relsPK-\0\0\0\0\0\0!\0��Ţ\0\0��\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0word/document.xmlPK-\0\0\0\0\0\0!\0����\0\0P\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0J\0\0word/theme/theme1.xmlPK-\0\0\0\0\0\0!\0��Z�\0\0J\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0word/settings.xmlPK-\0\0\0\0\0\0!\0ocP��\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0word/fontTable.xmlPK-\0\0\0\0\0\0!\0J؊��\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0� \0\0word/webSettings.xmlPK-\0\0\0\0\0\0!\05\nVKz\0\0�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�!\0\0docProps/app.xmlPK-\0\0\0\0\0\0!\0���q\0\0�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0p$\0\0docProps/core.xmlPK-\0\0\0\0\0\0!\0�M3�\0\0�9\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\'\0\0word/styles.xmlPK\0\0\0\0\0\0�\0\0K.\0\0\0\0','MCA403.docx',0,'MCA',4),('MCA404','Design and Analysis of Algorithms','PK\0\0\0\0\0!\0���7f\0\0 \0\0\0[Content_Types].xml �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�T�n�0�W�?D�V������8�H�`�\rX�K����nDUA*�)Y����ă�ښl	1i�J�/z,\'��nV���K~ϲ��)a���m ����j0�Hu�T�9bx�<�9X�\n�Q���\n��8�A�O1~���q���k6<A%��5}�*�`��k�����I)_:��%�1�ٜIs�\r�`� C]9N��{#k�V��E�WaI_����rai���N_UZB�_���%�D�[S�+���?�#��@�[�.z�9�>$N{9���+P9Y ��vu�GD����oR��w�ͳ�\r�Iʊ~���\Z8��W�Z�\"V0}����������>����uQwHo��\0\0\0��\0PK\0\0\0\0\0!\0�\Z��\0\0\0N\0\0\0_rels/.rels �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0���JA���a�}7�\n\"���H�w\"����w̤ھ�� �P�^����O֛���;�<�aYՠ؛`G�kxm��PY�[��g\rGΰino�/<���<�1��ⳆA$>\"f3��\\�ȾT��I	S����������W����Y\rig�@��X6_�]7\Z~\nf��ˉ�ao�.b*lI�r�j)�,\Zl0�%��b�\n6�i���D�_���,	�	���|u�Z^t٢yǯ;!Y,}{�C��/h>\0\0��\0PK\0\0\0\0\0!\0�d�Q�\0\0\01\0\0\0word/_rels/document.xml.rels �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0���j�0���{-;���ȹ�@���\0�����$���~�\nCR���`Fh擴��wo�\'�U�%)���:�(x/vw� ����qH��oo6�h4�C�v�DL���e�k)�l�ה8�6��.��������\r�U�>�0̀�\"S�+a_݃(���vuݕ���c���T�/<�!s��X\Zd3�� �����?\'g![�?��4���%�9����R�k6��$C�,�`&g�!/=�\0\0��\0PK\0\0\0\0\0!\0�_.�\n\0\0�i\0\0\0\0\0word/document.xml�\\�r�6�w���ᢓ�P;n�ʱ2�;�ď��t\r���\0����ݶ?�/�I��$K��NH�����㜋�~s�34$RQ���z�B�;¥|p`}�9����Ҙ��	N�1Q֛�����\\�>�\ZA\\��p��:h5\Z��U]��ž�>��S\Z>��à�?���(�z��m6_ZI3��\n%o%M�|�H�D_�GZ�ߧI�\'�}�?�I���!	�>�<\Z�Ik�����ޤ��2!�>��7\n��6W�|���	�R8D)8ۉ/�-�4��;@�D��}���\'>�<m�h���O?^>^#~w�4�	c�]�	wl�\Z�@�������|��59�!}2=�j�T�ȕ4j�\r�C:o��!fp���_�����*}M�>���E����Y����e�b�H������0o��&�����2+����D�2B���0���w���+�� ��=V�Q}�V\"Iw_�/W_��;��5`�M��$=3cR�^�Z�}~|X�k�Qt�8#t�1+���C6�j�WF&I��i�_�Լ���R;ΫT|�H*���FG>^�ݠ���Ag��<�����/��Y�K��HZ��;$QD�վ��&ɗ�*��j����ytg��X�)Ek�N��%A�a?؟@�迕�0����R\'��h��;T9�h2�M\n�c��e_K��.aQ�3H:�����B�q���-\"sb�����u,/�r���f���s�\\CNs@q���R�F\Z���82���-��7֋$���I�FV�gl8�����<0?Y�3z��ڻ�E���L��*$9�1~��-��,��:�n����DM��)f��FԀ�Cz.� � \0��-�JF�>:��1RĄzv>#%��+7����\\0Wal\n���Px\0ꉕ\"<�V`K$iqq�b���!�IF�����`�-Fpfb�2�1���	���>Ai�;p����U�gC��c(e4;�8�o��&M{#\r�V�� �]BO������\'��B�\\h���	F��$�M��a�y��T&���l��K�*�PQ�dn.f����<���SI�;F�_*�S5�t�#h�?��t��\\��h$H�Я���B3C����S?�+[ �Px2��s\Z��f�Ƥ397 K�==��a\0L�B	\n�:S�y�q�0m��cć��3������4P��<!��ˆ��	A�#���ŀX��-A@�p<EQ�L��\"ltrvr	�>/����ٓ)o�Ke1?�bʗ�j6vЬON�&�\rPC�J��U��)U�\'�M��ܙ���8���R�\r��P�O�G�y\0��i�Θc��5���i���,���~�ĩ��#B8r�E�0�|�������:P��J;�f�	��NS.D9dD mn�s�%�E~n��.E^4`�M.�,�)%��s�\rV��Q礭p׆�k�3�/g(18�INU�f�6��Yn+�n�P�*%D;29@��p>�\r����E\0\"�6	L(p���Y\n]�ڇ�,O��Vw�1��z|\'���<Yb�2��\"�q	xn�Y�e�\n����+��\\�����dg�ry\'�q���]�\n����yX�Q���c=�II���LнIIp�q�h���.��L��ӯe^�U�<Y6#�&�J�I\0��	������Fֻ���N�3/`��,q^%U�N2MD����Od�)]�\"P;YhZ�� hzsԚ��Sm�K�L����}7g�U�j˒W�w�+�=��?2�9m_�����t���Kv�ވ�B7��r�W���-��[G�B������ϸ��GLm�Qix�Gq�S�a�j��������c�]O[���|Q��:�� ���IΟnK,���:��\r����}v��#�ҽr�9Q�>�}�#L�H9$��M����C��K�ֿ�bd���\n�U����n��n���6x�c�nxK7�X?}]�b���N��r^k[b������6�y�\\嚩��5��mn�Ι4MW��9�\r���\\�%�#q�n���\0\0���V�r�0~��\Z��)�p��xL_`�e[�,y�u�pʃ�/�\'�J@�	3�9Q���e��v��/�t��~��:`��X��S�A-q���F�A��\Z����OC��$�-�ط�-�}m1y��Uz`���P9i!Cn�v��B�aл|�Z��E_E3��[��:g�1���[6�\r�u	�mU�({ܒط�]������\ng�eGwA	tg��:5>?\'s�f��{).����p��F�6��6��l�y�9�x��:�!��X�����s���z���\n%~F��q�\\-�f΢H1�[�\r�3���ӏ�Z�Pr� -��貪����k#�(m����<�; D(�Oj隥&�Z���2����?��i\"��z!E(�~8z1:��$�U9�_�����L#g�_�$�__,�����5[pf9�Z	d���~�`BQT YRh���^\Z6��#�H^���v�v��ck�A������?��S�_��]��rE+�#�[q�u��%�v��Ա�랧����mżF/��%����^�ş\"�ɝ�\0;z	L�﮼�p� z\Z���_�K���\0\0��\0PK\0\0\0\0\0!\0����\0\0P\0\0\0\0\0word/theme/theme1.xml�YOo�6��w toc\'v\Zu�ر�-M�n�i���P�@�I}��úa��m�a[�إ�4�:lЯ�GR��X^�6؊�>$���������!)O�^�r�C$�y@�����/�yH*��񄴽)�޵��߻��UDb�`}\"�qۋ�Jח���X^�)I`n�E���p)���li�V[]�1M<����\Z��O�P��6r�=���z�gb�Ig��u��S�eb���O������R�D۫����qu	�g��Z����o~ٺlAp�lx�pT0���+[}`j�����zA�\0�V�2�F���i�@�q�v�֬5\\|��ʜ̭N��le�X�ds���jcs����7����f����\r��W���+�Ն�7����`���g�Ș��J�\Z��j|��h(�K��D-����\0\rdX��iJ�؇(��x$(��:��;�˹!�I_�T��S1�������?E��?������?ZBΪm���U/������?�~����xY����\'���y5�g&΋/����ɋ�>���G�M�Ge���D�����3Vq%\'#q�����$�8��K�����)f�w9:ĵ��\nx}r�x����w���r�:\\TZaG�*�y8I�j�bR��c|XŻ�ǿ�I\nu3KG�nD1�NIB�s���\n��R��u���K>V�.EL+M2�#\'�f��i~�V��vl�{u8��z��H�\n�*���:�(W�☕\r~��J��T�e\\O*�tHG��HY��\0}KN��P�*ݾ˦���TѼ�9/#��A7�qZ��$*c?���qU��n��w�N��%��O��i�4=3ڗP��\n����1�P�m\\\\9���������Mؓ�2a�D�]�;Yt�\\��[x������]�}Wr��|�]��g-���\neW�\r�)6-r��CS�j��\ri�d	�DЇA�ΜIqbJ#x�꺃6k���#��A�Sh��&ʌt(Q�%��p%m��&]�caSl=�X�������\\P�1�Mh�9�M��V�dDA��aV�B��[݈fJ�íP|8�\ZքA�V^��f\r�H��n���-��\"�d>�z���n��Ǌ�	�ة�>�b���&����2��v����Kyϼ���D:����,AGm��\\nz��i�Ù��.uχYC�6�OMf��3o�r��$��5�����NH�T[XF64�T,ќ���M0�E)`#�5�XY�`�פ\0;��%�1�U�٥m;���R>QD����D�cp�U�\'��&LE�/p���m���%]�����8fi��r�S4�d7y\\�`�J�n���ί�I�R���3U�~7+���׸#��m�q�BiD����������i*�L6�9��m�Y\Z&�����i���HE��=(K&�N!V��.K�e�LD�ĕ�{D	�\Z���vEꦚde��NƟ��e�(�MN9ߜ\ZR�6����&3(��a����/D��U�z�<�{ˊ�Y��ȳ����V���)�9�Z[��4^n��5���!J��?��Q�3�eBo�C����M����m<�.�vp�����IY�f���Z�Y_p�[�=al-�Y�}Nc͙���ŋ4vfa��vl����\'S���A�8�|�*u�{��-�ߟ0%M0�7%����<���ҍ�\0\0\0��\0PK\0\0\0\0\0!\0�)R��\0\0J\0\0\0\0\0word/settings.xml�U�n�@}��@<�;vR��Hv�^�UI>`�Vٛv��;lH�(����sf��_^=p�6T�4\\��0 ��%u\Z���g��X%0)H\Z��	�6��]v�!֢�	PB�D�a�Eb��p03N-��쬐<�UE2����N��Z�D��4��T���`�\\�:\Z<�e�r\"l����H6\rUƫ��Uç\Z/r|-�#gޮ[įY��vR��o	�9(-bV��!]Tx�ޢ3��@s\r��Dd�m�-%�D]`A��qF���e�Y�i�c��\0>�%���6 �OI*h���<�R��0���(Y4���Dg\n\nT�Ia�dޮ�ߥ�I�4&<�â��pZC��p��ŧ�.�(���8w�%���q|�]����c\'&�.v��/1��2j�č�O��N{�<�Cz;๦ܸ���x���-��	0y�dm���l ��XO��LI��&U/�n@דr_V��Q�ŷG5�[�?k٪A�Ӡ��a��b�\Z����=n�<�^��	Պ��Q;�h*P�X\\y�*t\0Q��1�˜)v���}�\r(�c�&y�HCF��.�lY�����K^/Gn�sxs\\��e����G�\Zv汳	[yl5ak��\'��c�kN�0���~���J2&;R~�`\Z�\rE0\r(�}u���+��Ǆ<�6��Z��*ZrxH�e��{4Z�~��:%g���A	p��V=s�W�X��$Ł�N<��s>Ψ�Q��VjL�_����\'��\0\0��\0PK\0\0\0\0\0!\0ocP��\0\0\0\0\0\0\0word/fontTable.xml���n�0���H�\'�L�h���],��X�	mIO���;P�bf�d�4�;����|�շ*���(�NX4�,:5G�O	���^�,@���\"aW�l�|~ZT��h���8�	˝+�a�i.���BS-3V��O{\nM��T�����va��$��\0G��\\��Z�����ciM*�YUx?R�e�]P�5(�z�<X�J�ED�	�1��1��;��zea��`Q�n#�rJכ��D�R�4�����%�\'*�����8��nǼ%lD�z�)15�Y�g�)4j��i�D�Ƈ�iO5}�~>w$�R	>D|\Z�=��O�Ęx�d����Ʒ!�(j<^w���lHy�������x�ǉl@Q4��l�<��H�l�\'��Goe�����&	���d�\r	.\0\0\0��\0PK\0\0\0\0\0!\0J؊��\0\0\0\0\0\0\0\0word/webSettings.xml���j�0��{a�t_��0JHR(�/��\\Gi�d$m���5l��z����_im>Q42\r�m�A\n<E�\rpy?=�Q�4��	�F�����KW�zF���MUH;`1˝s\ZL^����6�$o����y��8|$$s��}u���Z�K�\nZyD+,S�ZC���%	����b�?xb9\nEqc����w\0\0\0��\0PK\0\0\0\0\0!\0OT1x\0\0�\0\0\0docProps/app.xml �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�R�N�0�#�Q�ӢBUm]�\"ā��О-g�X8�e���=kB� n��;k�gƆ�g���AY��\'�\"��H[)�,��~4ϳ������C����C��(LX�m�n�X�-v\"�ilhR[߉H�o��k%���}�&�iQ\\3��h*�F�D������_��ʤ/l˃#�J��s����\0(m�T��S����\'��v�W�Oon��%�[ᅌ���S`\0n��J�H��\'%�\r����wY\"\06����ޫxHB�-<*��\\�+��E�k\'p��F\n�k��k�;�����I�\"����J{���9��ܩ�n��I�|v=4<��r��	�\0<Ѓx�n��L��q��A�p��L>��Zߙ12~�2�\0\0��\0PK\0\0\0\0\0!\0���yq\0\0�\0\0\0docProps/core.xml �(�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0��MO�0��H��*�6M\'��N4.LBb�-$��&Q��ۿ\'�X�\nN�b���M�١*�=+���b�d�����rޡ�:*9-����Yq}�1�2e��(\r�	��\'I�2���s:�ز\rT�F^!}q�LE��\Zkʶt\r8��)��QN�\r0��H��ޙ�p���\n���D��Le�lh+�J���;��^�9늃�`� ��:�\'�~~�?ϯ���WP�q�:�J(2|>���}}s]z|��N��I����h��±V�[�6�|ˌ��?`%����-�����c���o�1��O(���!�۴�uC�igީ�>yx\\�Q��d&7K2M��4�?�uF��=]���7����\0\0��\0PK\0\0\0\0\0!\0�M3�\0\0�9\0\0\0\0\0word/styles.xml���s�8��o����!m��!0����\ni�[i48V�Rh�_��V]��wk�D�C��ծ������]��2_��Ϗ��LE~���.?>�+��fy�2��e|�U��͟��9Q�.�*2ruR,�����D%[�c����<��bǴ�,�\'r�	/�Î�z2=:�O\n�1m�j+�*.��`���\"�2�Jow���c\"���R���vȴ���EQ^�W��G�kݜ0�qi7!�D.�O���yҧJ����=�|k_�?�#�k߉T�U�̰�,[��iu��:��^����ϟ]���,ckm�.cV<[�Zc������A��\n\\ٳ�̝ᰍ�&�&%��	���b^]|?d�;hYB����͚�Ƥ�ԚD�\\���|�Y&?x����2��yu~QY}��_��Lss�w�HSn벼w�oE�����J������Pe��Drmܟ/�2�~�M��V�1�3��v@fͪ\Z:�{o܍n�W!�]�l9�+)�{A�a4hj#�\0vI��ƛx1����&�x���b�F?�f��F�*�I�2q�W��٫���#ZU48�U4�#Z528�U�#Z08�������h��wD�@��U4��@-�K�3n��\n��H�+[Mt�\nv]��6����v�X�k�s���b�̯҅g�tg�t���v�-S�|�L�t��_�uƣ���^��k�&�����%|+���%�u%��*����tndZ?�뭎V[h���yǤwτ��Y(����4�e�8*���6���Ⱛ��52wzNHs.�O�����\Z��&\0�k��>��\\��m�1��V�D��]�z�}�������=+~D�� ��3��bsȪ50(�\n�\\�E��DbA^��3:M��Nɹ��Q��G�ņ������\"\"\'���X㴖\0\"��w�Sؿ=Q�����\\γ�0-�\r�� ��7��C�����D�G�u�<,��\'��9���q�\0\Z�\n	�������=�	,�,�.e�V�Y�=���M��W���v�DP�	j�M���F/�}�\n�7���ѝ���R�\"��:�	 \"\n#�P�F�74^��!���\"k��Ժx#@�\n�W}��7D��v�ߌ��V��\r �\n9Am�FP���o^�TB���\n#�P�F�7F��0�\0��aH8�F����5�.�Y<�.��BцG�V�oo����x#(��4��\"X�5X^�,x�R%��T�FDF��0�\0�oh�xC7�E���u�F����Au�F����x�b��⍠��o�����z�C��	j��x#XP/���W�\n�DF��o(�x#@��{N�,�6xM��7D���7DֆG���oo����x#(��4Ջ7�ENP���\n#��h�F���\'�`Q�F��oh�xC7�E���u�F����Au�F���`�ٚ�����E��gP�j@�I��\0��\r/�a&>�;d$���@�(l����6v�:\n��LH��}�tjf������E����8(��;o���q!8�d?�����W;˭5s@��*�\0�Q�ss �<�c�s>�E8TUކ��-��gC��mT�5�Ĝ��A���$���w�G�dTn���￡�{�h����N��a�x�E���j�As8\\\Z��廒��:s����yj�5����\\b�[�̚�g<˾08����Ռo�{z|ݰaj-�����lO3`J��At�L~حyaN{���Wi��J{X�n߫K�__�{�^���V����\0\0��\0PK-\0\0\0\0\0\0!\0���7f\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0[Content_Types].xmlPK-\0\0\0\0\0\0!\0�\Z��\0\0\0N\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0_rels/.relsPK-\0\0\0\0\0\0!\0�d�Q�\0\0\01\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0word/_rels/document.xml.relsPK-\0\0\0\0\0\0!\0�_.�\n\0\0�i\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0word/document.xmlPK-\0\0\0\0\0\0!\0����\0\0P\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0I\0\0word/theme/theme1.xmlPK-\0\0\0\0\0\0!\0�)R��\0\0J\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\Z\0\0word/settings.xmlPK-\0\0\0\0\0\0!\0ocP��\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0word/fontTable.xmlPK-\0\0\0\0\0\0!\0J؊��\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0word/webSettings.xmlPK-\0\0\0\0\0\0!\0OT1x\0\0�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\0\0docProps/app.xmlPK-\0\0\0\0\0\0!\0���yq\0\0�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0m\"\0\0docProps/core.xmlPK-\0\0\0\0\0\0!\0�M3�\0\0�9\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0%\0\0word/styles.xmlPK\0\0\0\0\0\0�\0\0H,\0\0\0\0','MCA404.docx',0,'MCA',4),('MCA405','Java Programming and Technologies',NULL,NULL,0,'MCA',4),('MCA405L','Java Programming Lab',NULL,NULL,4,'MCA',4),('MCA406','Minor Project I',NULL,NULL,1,'MCA',4),('MCA407','Computer Graphics Lab',NULL,NULL,2,'MCA',4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
