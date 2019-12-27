#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Modified PakEditor2 by (Unknown)
Modified by Xebozone
All it does is extract the pak, it gets the name of the pak from the first and only command line arguement
*/

FILE *pakfile, *pabfile;
char is_pab = 0, debug = 0;
int pak_filesize, pab_filesize, join_filesize;

/* PAK header, used in the table */
int num_entries;
struct pak_entry_struct
{
       int extension;
       int offset;
       int filesize;
       int crc1;
       int reserved1;
       int crc2;
       int reserved2;
       int flags;
       char filename[160];
       int entry_start;
       int filesize_16;
};

struct pak_entry_struct *pak_entry = NULL;

/* Extensions, for files without filename */
char *crc_strings[] = { ".unk", ".wav", ".nav", ".stex", ".scn", ".last", ".shd", ".rnb_lvl", ".scv", ".table", ".nqb", ".rnb_mdl", ".mqb", ".dbg", ".sqb", ".mcol", ".skin", ".rag", ".mdv", ".pimg", ".col", ".ske", ".ska", ".skiv", ".fnt", ".geom", ".cel", ".hkc", ".tex", ".fnv", ".rnb", ".cam", ".mdl", ".fam", ".pfx", ".qb", ".imv", ".oba", ".img", ".png", ".tvx", ".fnc", ".gap", ".LAST" };
int crc_numbers[] = { 0x13371337, 0xA6808D4, 0x199F902B, 0x2B0A3095, 0x2C3B5ADC, 0x2CB3EF3B, 0x2F1A6A09, 0x33180544, 0x3F57C28A, 0x43904241, 0x49875607, 0x4A2E1FA0, 0x4BC1E85E, 0x559566CC, 0x5D796624, 0x6290993B, 0x64112E85, 0x6613EACD, 0x66AEDA37, 0x689028A5, 0x72A6D78C, 0x7330095C, 0x745DCD45, 0x777DB6D3, 0x7E1ABC70, 0x7EA7357B, 0x88493F06, 0x8A20E0F8, 0x8BFA5E8E, 0x9014DD5C, 0x91E1028D, 0x9B22CA94, 0x9BCC234D, 0x9DE9087F, 0xA7DEA591, 0xA7F505C4, 0xB065C9A2, 0xB0A32C18, 0xDAD5E950, 0xE20F226C, 0xEA151F1C, 0xFDC939B7, 0xFF2D0E91, 0xb524565f };


/* Gets the reference number for a CRC --> Extension */
unsigned int extensionGetReference(unsigned int crc32)
{
     int i;
     for (i = 0; i < sizeof(crc_numbers) >> 2; i++)
     {
         if (crc_numbers[i] == crc32)
         {
            return i;
         }
     }
//     printf("Unknown extension %X! (Will be extracted anyway with the extension \".unk\"). Press one key...\n", crc32);
     return 0;
}

/* Generates a table with all header info! */
void pakGenerateTable()
{
     int i;
     for (num_entries = 0; 1; num_entries++)
     {
           /* Get more memory... */
           pak_entry = realloc(pak_entry, (num_entries + 1) * sizeof(struct pak_entry_struct));
           
           /* Write the offset of the entry... */
           pak_entry[num_entries].entry_start = ftell(pakfile);
           
           /* Read the header! */
           fread(&pak_entry[num_entries], 32, 1, pakfile);
           
           /* Read the filename if present, get the extension if not... */
           if (pak_entry[num_entries].flags & 32)
              fread(&pak_entry[num_entries].filename, 160, 1, pakfile);
           else
           {
               sprintf(pak_entry[num_entries].filename, "unknown/%X%s", pak_entry[num_entries].crc1, crc_strings[extensionGetReference(pak_entry[num_entries].extension)]);
           }
           
           /* Change / with \ on filename... */
           for (i = 0; i < strlen(pak_entry[num_entries].filename); i++)
           {
               if (pak_entry[num_entries].filename[i] == '\\')
               {
                  pak_entry[num_entries].filename[i] = '/';
               }
           }
           
           
           /* Generate offset... */
           pak_entry[num_entries].offset += pak_entry[num_entries].entry_start;
           
           /* Generate padded filesize */
           if ((pak_entry[num_entries].filesize % 16) == 0)
              pak_entry[num_entries].filesize_16 = pak_entry[num_entries].filesize;
           else
               pak_entry[num_entries].filesize_16 = pak_entry[num_entries].filesize + (16 - (pak_entry[num_entries].filesize % 16));
           
           /* Check if PAB... */
           if (pak_entry[num_entries].offset > pak_filesize)
              is_pab = 1;

           if (debug & 1)
           {
              printf("Entry start: %i\nExtension: %X\nOffset: %i\nFilesize: %i\nPadded filesize: %i\nCRC1: %X\nReserved1: %i\nCRC2: %X\nReserved2: %i\nFlags: %i\nFilename: %s\n\n", pak_entry[num_entries].entry_start, pak_entry[num_entries].extension, pak_entry[num_entries].offset, pak_entry[num_entries].filesize, pak_entry[num_entries].filesize_16, pak_entry[num_entries].crc1, pak_entry[num_entries].reserved1, pak_entry[num_entries].crc2, pak_entry[num_entries].reserved2, pak_entry[num_entries].flags, pak_entry[num_entries].filename);
           }
           
           /* Break if CRC1 (Offset 12, DWORD) is 0 */
           if (pak_entry[num_entries].crc1 == 0)
              break;
     }
}

int main(int argc, char *argv[])
{
    /* PAK Editor 2 */
    char pakname[500];
    char pabname[500];
    
    /* Check if a parameter exists. If not, show syntax */
    if (argv[1] == NULL)
    {
				printf("\n\nPAK Extractor - A modification by Xebozone of PakEditor 2\nwith no bull and one purpose...TO EXTRACT!!!\n\n");
				printf("Error:\nsyntax is PakExtractor [Name]\n");
                exit(-1);
    }

	strcpy(pakname, argv[1]);
        
    /* Open the PAK */
    pakfile = fopen(pakname, "rb");
    if (pakfile == NULL)
    {
       printf("Can't open %s!\n", pakname);
       exit(-1);
    }
    
    /* Get the filesize... */
    fseek(pakfile, 0, SEEK_END);
    pak_filesize = ftell(pakfile);
    fseek(pakfile, 0, SEEK_SET);
    
    /* Generate the table... */
    pakGenerateTable();
    
    
    /* Put in memory & join if required... */    
    /* Add PAK */
    char *pak_mem;
    
    pak_mem = malloc(pak_filesize);
    if (pak_mem == NULL)
    {
       printf("Can't alloc %i bytes of memory (PAK File)!\n", pak_filesize);
       exit(-1);
    }
    
    fseek(pakfile, 0, SEEK_SET);        
    fread(pak_mem, pak_filesize, 1, pakfile);
    fclose(pakfile);
    
    if (is_pab == 0)
        join_filesize = pak_filesize;
    else
    {
        /* Open if & get filesize... */
        
        strcpy(pabname, pakname);
        memcpy(strstr(pabname, ".pak") + 3, "b", 1);
        
        pabfile = fopen(pabname, "rb");
        if (pabfile == NULL)
        {
           printf("Can't open %s!\n", pabname);
           exit(-1);
        }
        
        fseek(pabfile, 0, SEEK_END);
        pab_filesize = ftell(pabfile);
        fseek(pabfile, 0, SEEK_SET);
        
        /* Add PAB */
        pak_mem = realloc(pak_mem, pak_filesize + pab_filesize);
        if (pak_mem == NULL)
        {
           printf("Can't alloc %i bytes of memory (PAB File)!\n", pab_filesize);
           exit(-1);
        }
        
        fread(pak_mem + pak_filesize, pab_filesize, 1, pabfile);
        fclose(pabfile);
        
        join_filesize = pak_filesize + pab_filesize;
    }
    
    /* Create the directories & extract... */
    int i;
    char *posslash, temp_dir[160], *buffer;
    FILE *output;
    
    for (i = 0; i < num_entries; i++)
    {
        /* Make dirs */       
        posslash = strchr(pak_entry[i].filename, '/');
        while (posslash != NULL)
         {
               memcpy(temp_dir, pak_entry[i].filename, posslash - pak_entry[i].filename);
               /* Add null-terminating character */
               temp_dir[posslash - pak_entry[i].filename] = 0;
               /* Create dir */
               mkdir(temp_dir);
               posslash = strchr( posslash + 1, '/');
         }
         
         /* Extract */
         buffer = malloc(pak_entry[i].filesize);
         if (buffer == 0)
         {
            printf("Can't alloc %i bytes!\n", pak_entry[i].filesize);
            exit(-1);
         }
         
         output = fopen(pak_entry[i].filename, "wb");
         if (output == NULL)
         {
            printf("Can't open %s!\n", pak_entry[i].filename);
            exit(-1);
         }
         
         memcpy(buffer, pak_mem + pak_entry[i].offset, pak_entry[i].filesize);
         fwrite(buffer, pak_entry[i].filesize, 1, output);
         fclose(output);
         free(buffer);
     }
     
     /* Read all entries & extract... */
    
    free(pak_entry);
    free(pak_mem);
	printf("\nExtraction of %s Success!", pakname);
    return 0;
}
