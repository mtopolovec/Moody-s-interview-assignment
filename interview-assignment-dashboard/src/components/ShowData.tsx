import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';

type Props = {
  data: ResponseData;
};

export interface ResponseData {
  searchResults: SearchResult[];
}

interface SearchResult {
  countryCode: string;
  countryName: string;
  registrationNumber: string;
  name: string;
  status: string;
  sources: Sources;
}

interface Sources {
  lastCheckedTime: string;
}

export default function ShowData({ data }: Props) {
  return (
    <>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Country Code</TableCell>
              <TableCell>Country Name</TableCell>
              <TableCell>Registration Number</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Last Checked Time</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.searchResults.map((result, index) => (
              <TableRow key={index}>
                <TableCell>{result.countryCode}</TableCell>
                <TableCell>{result.countryName}</TableCell>
                <TableCell>{result.registrationNumber}</TableCell>
                <TableCell>{result.name}</TableCell>
                <TableCell>{result.status}</TableCell>
                <TableCell>
                  {new Date(result.sources.lastCheckedTime).toLocaleString()}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}
