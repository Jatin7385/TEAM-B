import React, { useState, useEffect } from 'react';
import { useTable } from 'react-table';
import probe from './images/probe.png';

function Table1() {

 const data = React.useMemo(
     () => [
       {
         col1: 'Mass (with electronic subsystems)',
         col2: '410g',
         
       },
       {
         col1: 'Mass (without electronic subsystems)',
         col2: '307g',
         
       },
       {
         col1: 'Height',
         col2: '184.50mm',
         
       },
       {
        col1: 'Diameter',
        col2: '123mm',
        
      },
     ],
     []
 )

 const columns = React.useMemo(
     () => [
       {
         Header: 'Constraints',
         accessor: 'col1', // accessor is the "key" in the data
       },
       {
         Header: 'Values',
         accessor: 'col2',
       },
       
     ],
     []
 )

 const {
   getTableProps,
   getTableBodyProps,
   headerGroups,
   rows,
   prepareRow,
 } = useTable({ columns, data })

 return (
   <>
  
     <div>
       
       <table {...getTableProps()} style={{ border: 'solid 1px grey', marginLeft:"30px", position:"absolute",top:"260%" }}>
         <thead>
         {headerGroups.map(headerGroup => (
             <tr {...headerGroup.getHeaderGroupProps()}>
               {headerGroup.headers.map(column => (
                   <th
                       {...column.getHeaderProps()}
                       style={{
                         borderBottom: 'solid 7px blue',
                         color: 'white',
                         fontSize:'30px'
                       }}
                   >
                     {column.render('Header')}
                   </th>
               ))}
             </tr>
         ))}
         </thead>
         <tbody {...getTableBodyProps()}>
         {rows.map(row => {
           prepareRow(row)
           return (
               <tr {...row.getRowProps()}>
                 {row.cells.map(cell => {
                   return (
                       <td
                           {...cell.getCellProps()}
                           style={{
                             padding: '18px',
                             border: 'solid 1px gray',
                             color:'white',
                             fontSize:'25px',


                           }}
                       >
                         {cell.render('Cell')}
                       </td>
                   )
                 })}
               </tr>
           )
         })}
         </tbody>
       </table>
       <img src={probe} style={{width:"900px", height:"738px",border:"black",marginLeft:"780px",position:"absolute",top:"239%"}} />
     </div>
     </>
 );
}

export default Table1;